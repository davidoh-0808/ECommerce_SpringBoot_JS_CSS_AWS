package com.application.gentlegourmet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String loadHome(Model model) {
        model.addAttribute("testValue", "This is the testValue from HomeController");

        return "home";
    }

}
