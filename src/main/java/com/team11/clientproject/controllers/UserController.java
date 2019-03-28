package com.team11.clientproject.controllers;

import com.team11.clientproject.dtos.User;
import com.team11.clientproject.repositories.UsersRepository;
import com.team11.clientproject.services.ConfigurationService;
import com.team11.clientproject.services.TutorialsService;
import com.team11.clientproject.services.UsersService;
import com.team11.clientproject.forms.RegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class UserController {
    private final
    UsersService usersService;

    private final
    PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;
    private final TutorialsService tutorialsService;
    private final ConfigurationService configurationService;

    @Autowired
    public UserController(UsersService usersService, PasswordEncoder passwordEncoder, ConfigurationService configurationService, TutorialsService tutorialsService, UsersRepository usersRepository) {
        this.usersService = usersService;
        this.passwordEncoder = passwordEncoder;
        this.configurationService = configurationService;
        this.tutorialsService = tutorialsService;
        this.usersRepository = usersRepository;
    }

    /**
     * Shows the home page
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homePage() {
        return "homePage";
    }

    /**
     * Shows the admin page. This is here as the admin controller maps to /admin/stats
     */
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage() {
        return "adminPage";
    }

    /**
     * Shows the login page
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "loginPage";
    }


    /**
     * Handles 403s
     */
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String AccessDenied() {
        return "403Page";
    }

    /**
     * Shows the register form
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegForm() {
        return "registrationPage";
    }

    /**
     * Handles the register request
     */

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String submitForm(@Valid RegistrationForm registrationForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", true);
            return "registrationPage";
        }
        String encryptedUserPass = passwordEncoder.encode(registrationForm.getPassword());
        var user = new User();
        user.setEmail(registrationForm.getEmailAddress());
        user.setPassword(encryptedUserPass);
        user.setUsername(registrationForm.getUsername());
        user.setFirstName(registrationForm.getFirstName());
        user.setLastName(registrationForm.getLastName());
        usersService.registerUser(user);
        model.addAttribute("emailValidation", configurationService.getConfiguration(ConfigurationService.EMAIL_CONFIGURATION));
        return "registerSuccess";
    }

    @RequestMapping("/activate/{uuid}")
    public String activate(@PathVariable String uuid, Model model) {
        model.addAttribute("success", usersService.activateUser(uuid));
        return "activated";
    }

    @GetMapping("/tutorial")
    public String tutorial(Authentication authentication) {
        var user = usersService.getUserByUsername(((UserDetails) authentication.getPrincipal()).getUsername());
        if (tutorialsService.hasUserCompletedTutorial(user, tutorialsService.getTutorialByName("INTRODUCTION"))) {
            return "redirect:/";

        }

        return "tutorial";
    }

    @PostMapping("/tutorial")
    public String finishTutorial(Authentication authentication) {
        var user = usersService.getUserByUsername(((UserDetails) authentication.getPrincipal()).getUsername());
        tutorialsService.saveUserTutorialCompletion(user, tutorialsService.getTutorialByName("INTRODUCTION"), null);
        return "redirect:/vote/do";

    }


}
