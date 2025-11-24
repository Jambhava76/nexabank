package com.nexa.bank.nexabank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Landing Page
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // Choose Access Page
    @GetMapping("/choose")
    public String choosePage() {
        return "choose";
    }


}
