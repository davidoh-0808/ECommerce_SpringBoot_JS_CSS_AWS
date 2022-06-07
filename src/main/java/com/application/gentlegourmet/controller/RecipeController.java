package com.application.gentlegourmet.controller;

import com.application.gentlegourmet.entity.Product;
import com.application.gentlegourmet.entity.Recipe;
import com.application.gentlegourmet.service.ProductService;
import com.application.gentlegourmet.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@Controller
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final ProductService productService;


    /////////////////////////////////////////////////////////////////////////////////////////////


    @GetMapping("/recipe-list")
    public String showRecipeListPage(Model model) {
        Set<Recipe> recipes = recipeService.findAllRecipesAllFields();

        model.addAttribute("recipes", recipes);

        return "cook/recipe-list";
    }


    @GetMapping("/recipe/{recipeId}")
    public String showRecipePage(@PathVariable("recipeId") Long recipeId, Model model) {
        /* insert prepped product inside recipe entity instead
        Product product = productService.findProductByRecipe(recipe);
        model.addAttribute("product", product);
         */
        Recipe recipe = recipeService.findRecipeAllFieldsByRecipeId(recipeId);
        model.addAttribute("recipe", recipe);


        return "cook/recipe";
    }


    /////////////////////////////////////////////////////////////////////////////////////////////




}
