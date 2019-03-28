package com.team11.clientproject.controllers.api;

import com.team11.clientproject.repositories.TutorialCompletionRepository;
import com.team11.clientproject.repositories.TutorialsRepository;
import com.team11.clientproject.services.TutorialsService;
import com.team11.clientproject.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/tutorial")
public class TutorialApiController {
    private final TutorialsService tutorialsService;
    private final UsersService usersService;

    @Autowired
    public TutorialApiController(TutorialsService tutorialsService, UsersService usersService) {
        this.tutorialsService = tutorialsService;
        this.usersService = usersService;
    }


    @PostMapping("/complete")
    public ResponseEntity complete(@RequestParam String name, Authentication authentication, @RequestParam(required = false) Integer score) {
        var tutorial = tutorialsService.getTutorialByName(name);
        var user = usersService.getUserByUsername(((UserDetails) authentication.getPrincipal()).getUsername());
        tutorialsService.saveUserTutorialCompletion(user, tutorial, score);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Says if a certain tutorial is completed by the user
     *
     * @param name the name of the tutorial
     * @return true/false depending on if t he user has completed it
     */
    @GetMapping("/hasCompleted")
    public boolean hasCompleted(@RequestParam String name, Authentication authentication) {
        var user = usersService.getUserByUsername(((UserDetails) authentication.getPrincipal()).getUsername());
        return tutorialsService.hasUserCompletedTutorial(user, tutorialsService.getTutorialByName(name));
    }

    @PostMapping("/removeCompletion")
    public ResponseEntity removeCompletion(@RequestParam String name, Authentication authentication) {
        var user = usersService.getUserByUsername(((UserDetails) authentication.getPrincipal()).getUsername());
        tutorialsService.removeTutorialCompletion(user, tutorialsService.getTutorialByName(name));
        return new ResponseEntity(HttpStatus.OK);
    }
}
