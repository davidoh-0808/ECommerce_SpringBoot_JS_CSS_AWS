package com.application.gentlegourmet.service;

import com.application.gentlegourmet.entity.Recipe;
import com.application.gentlegourmet.entity.RecipeImage;
import com.application.gentlegourmet.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeImageService recipeImageService;

    //////////////////////////////////////////////////////////////////////////////////

    public Set<Recipe> findAllRecipesNameAndProductOnly() {
        return recipeRepository.findAllRecipesNameAndProductOnly();
    }

    public Set<Recipe> findAllRecipesAllFields() {
        Set<Recipe> recipeSet = recipeRepository.findAllRecipesAllFieldsExceptDetails();

        //attach recipe thumbnail before return
        return attachRecipeThumnbnails(recipeSet);
    }

    //////////////////////////////////////////////////////////////////////////////////

    //attach image thumbnail to each recipe
    private Set<Recipe> attachRecipeThumnbnails(Set<Recipe> recipeSet) {
        for(Recipe r : recipeSet) {
            List<RecipeImage> recipeImageList = recipeImageService.findImagesByRecipe(r);
            String recipeThumbnailPath = recipeImageList.get(0).getPath();

            r.setRecipeThumbnailPath(recipeThumbnailPath);
        }

        return recipeSet;
    }

}
