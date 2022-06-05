package com.application.gentlegourmet.controller;

import com.application.gentlegourmet.entity.Product;
import com.application.gentlegourmet.entity.Recipe;
import com.application.gentlegourmet.service.ProductService;
import com.application.gentlegourmet.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;
    private final RecipeService recipeService;

    @GetMapping("/home")
    public String loadHome(Model model) {
        //Carousel : Bestsellers carousel
        List<Product> bestsellerProducts = productService.findTopFiveBestsellingProducts();
        //Carousel : Recommended For You
        List<Product> recommendedProducts = productService.findTopFiveRecommendedProducts();
        //Banner : Recipes
        Set<Recipe> recipes = recipeService.findAllRecipesNameAndProductOnly();

        model.addAttribute("bestsellerProducts", bestsellerProducts);
        model.addAttribute("recommendedProducts", recommendedProducts);
        model.addAttribute("recipes", recipes);

        return "home";
    }

}
