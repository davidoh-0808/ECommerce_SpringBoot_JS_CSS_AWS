package com.application.gentlegourmet.service;

import com.application.gentlegourmet.entity.Recipe;
import com.application.gentlegourmet.entity.RecipeImage;
import com.application.gentlegourmet.repository.RecipeImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeImageService {

    private final RecipeImageRepository recipeImageRepository;

    //////////////////////////////////////////////////////////////////////////////////////

    public List<RecipeImage> findImagesByRecipe(Recipe recipe) {

        return recipeImageRepository.findImagesByRecipe(recipe);
    }

}
