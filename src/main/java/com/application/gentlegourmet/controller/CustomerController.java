package com.application.gentlegourmet.controller;

import org.springframework.stereotype.Controller;


@Controller
public class CustomerController {

    /* this is replaced by Spring Security -> WebSecurityConfig.java
    @PostMapping("/customer-signin")
    public String HandleCustomerSignin() {
        System.out.println("************** /customer-signin url endpoint received ..");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "home"; // logicalname
        }

        return "redirect:/home";
    }*/



}
