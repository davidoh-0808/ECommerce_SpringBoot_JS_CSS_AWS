package com.application.gentlegourmet.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CustomerController {

    // loginProcessingUrl from WebSecurityConfig
    @PostMapping("/customer-signin")
    public String HandleCustomerSignin() {
        System.out.println("************** /customer-signin url endpoint received ..");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "home"; // logicalname
        }

        return "redirect:/home";
    }

}
