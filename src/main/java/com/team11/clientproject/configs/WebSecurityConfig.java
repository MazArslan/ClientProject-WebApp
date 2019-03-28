package com.team11.clientproject.configs;

import com.team11.clientproject.repositories.TrackUserLoginRepository;
import com.team11.clientproject.services.EqualityDiversityService;
import com.team11.clientproject.services.TutorialsService;
import com.team11.clientproject.services.UsersService;
import com.team11.clientproject.utils.AuthenticationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
// Login system initially based on: https://o7planning.org/en/11543/create-a-login-application-with-spring-boot-spring-security-spring-jdbc
// accessed ON 22/11/2018
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    final private UserDetailsService userDetailsService;

    private final UsersService usersService;

    private final TrackUserLoginRepository trackUserLoginRepository;
    private final TutorialsService tutorialsService;

    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsService, EqualityDiversityService equalityDiversityService, UsersService usersService, TrackUserLoginRepository trackUserLoginRepository, TutorialsService tutorialsService) {
        this.userDetailsService = userDetailsService;
        this.equalityDiversityService = equalityDiversityService;
        this.usersService = usersService;
        this.trackUserLoginRepository = trackUserLoginRepository;
        this.tutorialsService = tutorialsService;
    }

    private final EqualityDiversityService equalityDiversityService;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        var handler = new AuthenticationHandler(equalityDiversityService, usersService, trackUserLoginRepository, tutorialsService);
        httpSecurity.csrf().disable();

        //page doesn't require login
        httpSecurity.authorizeRequests().antMatchers("/", "/login", "/register").permitAll();

        //has user login
        httpSecurity.authorizeRequests().antMatchers("/equality", "/feedback", "/vote/**", "/api/**", "/highscore", "/tutorial").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN");

        //for admin only
        httpSecurity.authorizeRequests().antMatchers("/admin/**").hasAuthority("ROLE_ADMIN");

        // if invalid user role attempts to access
        httpSecurity.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");


        httpSecurity.authorizeRequests().and().formLogin()
                .loginProcessingUrl("/login")
                .loginPage("/login")
                .successHandler(handler)
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password");

        httpSecurity.logout().logoutUrl("/logout").logoutSuccessHandler(handler);

        httpSecurity.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(false).expiredSessionStrategy(event -> event.getSessionInformation().expireNow());
    }


}
