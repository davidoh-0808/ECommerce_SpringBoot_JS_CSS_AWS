package com.application.gentlegourmet.controller;

import com.application.gentlegourmet.service.CartService;
import com.application.gentlegourmet.service.ProductService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class CheckoutController {

    private final ProductService productService;
    private final CartService cartService;

    @GetMapping("/checkout")
    public String goToCheckout(Model model) {

        //1) check for authentication principal
        if(SecurityContextHolder.getContext().getAuthentication() != null &&
        SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
        !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {

            //2) for the current authenticated user, list cart items


            return "checkout";
        } else {
            model.addAttribute("errorMessage", "Please login before checking out items :)");

            return "redirect:/home";
        }

    }




}
