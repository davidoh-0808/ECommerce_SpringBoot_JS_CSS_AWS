package com.application.gentlegourmet.controller;

import com.application.gentlegourmet.entity.Recipe;
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

    //////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/recipe-list")
    public String showRecipeListPage(Model model) {
        Set<Recipe> recipes = recipeService.findAllRecipesAllFields();

        model.addAttribute("recipes", recipes);

        return "cook/recipe-list";
    }


    @GetMapping("/recipe/{recipeId}")
    public String showRecipeListPage(@PathVariable("recipeId") Long recipeId, Model model) {
//        Recipe recipe = recipeService.findRecipeById(Long recipeId);
//
//        model.addAttribute("recipe", recipe);

        return "cook/recipe";
    }

}
