package com.application.gentlegourmet.controller;

import com.application.gentlegourmet.entity.Hashtag;
import com.application.gentlegourmet.service.CartService;
import com.application.gentlegourmet.service.HashtagService;
import com.application.gentlegourmet.service.ProductService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class CheckoutController {

    private final ProductService productService;
    private final CartService cartService;
    private final HashtagService hashtagService;

    @GetMapping("/checkout")
    public String goToCheckout(Model model) {

        //1) check for authentication principal
        if(SecurityContextHolder.getContext().getAuthentication() != null &&
        SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
        !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {

            //HashtagService to provide top 5 hashtags
            List<Hashtag> hashtagList = hashtagService.getTopFiveSearchedHashtags();


            //2) for the current authenticated user, list cart items



            model.addAttribute("hashtagList", hashtagList);

            return "checkout";
        } else {
            model.addAttribute("errorMessage", "Please login before checking out items :)");

            return "redirect:/home";
        }

    }




}
