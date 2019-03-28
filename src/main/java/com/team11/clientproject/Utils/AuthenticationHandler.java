package com.team11.clientproject.utils;

import com.team11.clientproject.repositories.TrackUserLoginRepository;
import com.team11.clientproject.services.EqualityDiversityService;

import com.team11.clientproject.services.TutorialsService;
import com.team11.clientproject.services.UsersService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionListener;
import java.io.IOException;

public class AuthenticationHandler implements HttpSessionListener, AuthenticationSuccessHandler, LogoutSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private EqualityDiversityService equalityDiversityService;
    private UsersService usersService;
    private TutorialsService tutorialsService;
    private TrackUserLoginRepository trackUserLoginRepository;


    public AuthenticationHandler(EqualityDiversityService equalityDiversityService, UsersService usersService, TrackUserLoginRepository trackUserLoginRepository, TutorialsService tutorialsService) {
        this.equalityDiversityService = equalityDiversityService;
        this.usersService = usersService;
        this.trackUserLoginRepository = trackUserLoginRepository;
        this.tutorialsService = tutorialsService;
    }

    private String findTargetURL(Authentication authentication) {
        var user = usersService.getUserByUsername(((UserDetails) authentication.getPrincipal()).getUsername());
        var userComplete = equalityDiversityService.findUserCompletion(user);
        if (!userComplete) {
            return "/equality";
        } else if (!tutorialsService.hasUserCompletedTutorial(user, tutorialsService.getTutorialByName("INTRODUCTION"))) {
            return "/tutorial";
        } else {
            return "/vote/do";
        }
    }

    private void userLogin(Authentication authentication){
        var user = usersService.getUserByUsername(((UserDetails) authentication.getPrincipal()).getUsername());
        trackUserLoginRepository.userLogin(user);
    }

    private void userLogout(Authentication authentication){
        var user = usersService.getUserByUsername(((UserDetails) authentication.getPrincipal()).getUsername());
//        trackUserLoginRepository.userLogout(user);
    }


    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        request.getSession().setMaxInactiveInterval(60*30);
        String url = findTargetURL(authentication);
//        userLogin(authentication);
        redirectStrategy.sendRedirect(request, response, url);
    }



    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        userLogout(authentication);
        redirectStrategy.sendRedirect(request, response, "/");
    }


}
