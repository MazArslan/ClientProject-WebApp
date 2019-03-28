package com.team11.clientproject.controllers;

import com.team11.clientproject.dtos.EqualityDiversity;
import com.team11.clientproject.forms.EqualityDiversityForm;
import com.team11.clientproject.services.EqualityDiversityService;
import com.team11.clientproject.services.UsersService;
import com.team11.clientproject.utils.StringUtils;
import com.team11.clientproject.utils.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/equality")
@Controller
public class EqualityDiversityController {

    private EqualityDiversityService equalityDiversityService;
    private final UsersService usersService;

    @Autowired
    public EqualityDiversityController(EqualityDiversityService equalityDiversityService, UsersService usersService) {
        this.equalityDiversityService = equalityDiversityService;
        this.usersService = usersService;
    }

    /**
     * Shows the equality-diversity form.
     */
    @GetMapping("")
    public String equalityMappingForm(Authentication authentication) {

        var user = usersService.getUserByUsername(((UserDetails) authentication.getPrincipal()).getUsername());
        if (equalityDiversityService.findUserCompletion(user)) {
            return "redirect:/";
        }
        return "equalityForm";
    }

    /**
     * Saves the equality/diversity entry.
     *
     * @return either the form or send the user to voting.
     */
    @PostMapping("")
    public String equalityDiversitySubmit(@Valid EqualityDiversityForm equalityDiversityForm, BindingResult bindingResult, Model model , Authentication authentication) {
        var user = usersService.getUserByUsername(((UserDetails) authentication.getPrincipal()).getUsername());


        var convertedEqualityDiversityForm = StringUtils.convertEmptyStringsIntoNulls(equalityDiversityForm);
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", true);
            return "equalityForm";
        }
        System.out.println("Ethnicity is:" + convertedEqualityDiversityForm.getEthnicity());
        var rowToAdd = new EqualityDiversity(convertedEqualityDiversityForm.getGender(),
                convertedEqualityDiversityForm.getSexuality(), convertedEqualityDiversityForm.getEthnicity(),
                convertedEqualityDiversityForm.getCountry(), convertedEqualityDiversityForm.getPostcodeStart(),
                user);
        equalityDiversityService.insertEntry(rowToAdd);
        return "redirect:/tutorial";
    }
}
