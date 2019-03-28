package com.team11.clientproject.controllers;

import com.team11.clientproject.dtos.Highscore;
import com.team11.clientproject.repositories.HighscoreRepository;
import com.team11.clientproject.services.HighscoreService;
import com.team11.clientproject.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequestMapping("/highscore")
public class HighscoreController {
    private final UsersService usersService;
    private final HighscoreService highscoreService;

    @Autowired
    public HighscoreController(UsersService usersService, HighscoreService highscoreService) {
        this.usersService = usersService;
        this.highscoreService = highscoreService;
    }

    /**
     * Gets the highscore board
     *
     * @param authentication the user  for whom to get the highscore board
     */
    @GetMapping("")
    public String getHighscoreBoard(Authentication authentication, Model model) {
        var user = usersService.getUserByUsername(((UserDetails) authentication.getPrincipal()).getUsername());
        model.addAttribute("highscores", highscoreService.getHighscoresByUser(user));
        return "highscores";

    }
}
