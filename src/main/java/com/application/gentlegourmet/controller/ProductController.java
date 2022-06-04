package com.application.gentlegourmet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductController {

    /**
    send the product entity of the given id to product.html page
     */
    @GetMapping("/product/{productId}")
    public String showProductPage(@PathVariable Long productId, Model model) {


        return "product/product";
    }

}
