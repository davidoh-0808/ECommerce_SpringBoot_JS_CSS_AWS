package com.application.gentlegourmet.controller;

import com.application.gentlegourmet.entity.Cart;
import com.application.gentlegourmet.entity.Customer;
import com.application.gentlegourmet.entity.Product;
import com.application.gentlegourmet.service.CartService;
import com.application.gentlegourmet.service.CustomerService;
import com.application.gentlegourmet.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/add-to-cart/{productId}")
    public String addToCart(@PathVariable("productId") Long productId, RedirectAttributes redirectAttr, HttpServletRequest request) {
        // common variable for page redirect
        String referer = request.getHeader("Referer");

        //1) check for authentication principal (aka. "is someone logged in?")
        if(SecurityContextHolder.getContext().getAuthentication() != null &&
        SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
        !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {

            //2) add item to Cart
            cartService.addToCart(productId);

            //3) config redirectAttr and redirect to previous page
            redirectAttr.addAttribute("testMessage", null).addFlashAttribute("cartSuccessMessage", "The Product was added successfully :)");

            return "redirect:" + referer;

        } else {
            //error message for the view to alert
            redirectAttr.addAttribute("testMessage", null).addFlashAttribute("cartFailureMessage", "Adding to cart requires login :)");
            return "redirect:" + referer;

        }

    } //end of addToCart


    @GetMapping("/empty-cart")
    public String emptyCart(RedirectAttributes redirectAttr, HttpServletRequest request) {
        // common variable for page redirect
        String referer = request.getHeader("Referer");

        //1) check for authentication principal (aka. "is someone logged in?")
        if(SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {

            //2) remove the entire Cart
            cartService.emptyCart();

            //3) config redirectAttr and redirect to previous page
            redirectAttr.addAttribute("testMessage", null).addFlashAttribute("successMessage", "The Cart was emptied successfully :)");

            return "redirect:" + referer;

        } else {
            //error message for the view to alert
            redirectAttr.addAttribute("testMessage", null).addFlashAttribute("errorMessage", "Emptying cart requires login :)");
            return "redirect:" + referer;

        }

    }


}
