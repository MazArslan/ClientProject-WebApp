package com.team11.clientproject.controllers;

import com.team11.clientproject.forms.FeedbackForm;
import com.team11.clientproject.repositories.FeedbackRepository;
import com.team11.clientproject.repositories.FeedbackRepositoryImpl;
import com.team11.clientproject.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.validation.Valid;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    //gets the feedback form
    @GetMapping()
    public String displayFeedbackForm(){
        return "feedbackForm";
    }

    //Responds to the data on submission
    @PostMapping()
    public String submitFeedbackForm(@Valid FeedbackForm feedbackForm, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            model.addAttribute("errors", true);
            return "/feedback";
        }

        feedbackService.addFeedback(feedbackForm.getQuestion1());

        return "feedbackSuccess";
    }


}
