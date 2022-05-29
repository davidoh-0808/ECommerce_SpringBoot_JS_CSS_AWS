package com.application.gentlegourmet.controller;

import com.application.gentlegourmet.entity.Product;
import com.application.gentlegourmet.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;

    @GetMapping("/home")
    public String loadHome(Model model) {
        model.addAttribute("testValue", "This is the testValue from HomeController");

        /*
         *** testing thymeleaf ***
        Product product = productService.findProductById(2L);
        model.addAttribute("product", product)
        */

        return "home";
    }

}
