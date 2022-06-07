package com.application.gentlegourmet.service;

import com.application.gentlegourmet.entity.Product;
import com.application.gentlegourmet.entity.Recipe;
import com.application.gentlegourmet.entity.RecipeImage;
import com.application.gentlegourmet.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeImageService recipeImageService;
    private final ProductImageService productImageService;


    //////////////////////////////////////////////////////////////////////////////////


    public Set<Recipe> findAllRecipesNameAndProductOnly() {
        return recipeRepository.findAllRecipesNameAndProductOnly();
    }

    public Set<Recipe> findAllRecipesAllFields() {
        Set<Recipe> recipeSet = recipeRepository.findAllRecipesAllFieldsExceptDetails();

        //attach recipe thumbnail before return
        return attachRecipeThumnbnails(recipeSet);
    }


    public Recipe findRecipeAllFieldsByRecipeId(Long recipeId) {
        Recipe recipeFound = recipeRepository.findRecipeAllFieldsByRecipeId(recipeId);

        //prep recipe ingredient for view (one string -> List of strings)
        Recipe recipe = convertRecipeIngredientToSet(recipeFound);

        //prep Product entity field for view (get product and insert product thumbnail)
        Product productField = recipe.getProduct();
        String productThumbnailPath = productImageService.findImagesByProduct(productField).get(0).getPath();
        productField.setProductThumbnailPath(productThumbnailPath);

        return attachRecipeThumbnail(recipe);
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


    private Recipe convertRecipeIngredientToSet(Recipe recipe) {
        String ingredients = recipe.getIngredients();
        List<String> ingredientList = Arrays.asList( ingredients.split("/") );

        recipe.setIngredientList(ingredientList);

        return recipe;
    }


    private Recipe attachRecipeThumbnail(Recipe recipe) {
        List<RecipeImage> recipeImageList = recipeImageService.findImagesByRecipe(recipe);
        String recipeThumbnailPath = recipeImageList.get(0).getPath();

        recipe.setRecipeThumbnailPath(recipeThumbnailPath);

        return recipe;
    }

}
