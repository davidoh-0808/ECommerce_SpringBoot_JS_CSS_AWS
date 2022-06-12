package com.application.gentlegourmet.controller;

import com.application.gentlegourmet.entity.Product;
import com.application.gentlegourmet.entity.ProductSearch;
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
    public String getRecipeListPage(Model model) {
        Set<Recipe> recipes = recipeService.findAllRecipesAllFields();
        //need a productSearch object(a DTO, not entity) for search request
        ProductSearch productSearch = new ProductSearch();

        model.addAttribute("recipes", recipes);
        model.addAttribute("productSearch", productSearch);

        return "cook/recipe-list";
    }


    @GetMapping("/recipe/{recipeId}")
    public String getRecipePage(@PathVariable("recipeId") Long recipeId, Model model) {
        /* insert prepped product inside recipe entity instead
        Product product = productService.findProductByRecipe(recipe);
        model.addAttribute("product", product);
         */
        Recipe recipe = recipeService.findRecipeAllFieldsByRecipeId(recipeId);
        //need a productSearch object(a DTO, not entity) for search request
        ProductSearch productSearch = new ProductSearch();

        model.addAttribute("recipe", recipe);
        model.addAttribute("productSearch", productSearch);

        return "cook/recipe";
    }


    /////////////////////////////////////////////////////////////////////////////////////////////




}
