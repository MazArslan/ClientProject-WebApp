package com.team11.clientproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vote")
public class VoteController {
    /**
     * Shows the voting page
     */
    @GetMapping("/do")
    public String votePage() {
        return "vote";
    }
}
