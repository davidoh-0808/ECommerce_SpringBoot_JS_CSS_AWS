package com.application.gentlegourmet.controller;

import com.application.gentlegourmet.entity.Product;
import com.application.gentlegourmet.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;

    @GetMapping("/home")
    public String loadHome(Model model) {
        //Carousel : Bestsellers carousel
        List<Product> bestsellerProducts = productService.findTopFiveBestsellingProducts();

        //Carousel : Recommended For You



        model.addAttribute("bestsellerProducts", bestsellerProducts);

        return "home";
    }

}
