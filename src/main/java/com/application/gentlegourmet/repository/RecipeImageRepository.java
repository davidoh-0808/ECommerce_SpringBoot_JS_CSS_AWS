package com.application.gentlegourmet.repository;

import com.application.gentlegourmet.entity.Recipe;
import com.application.gentlegourmet.entity.RecipeImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeImageRepository extends JpaRepository<RecipeImage, Long> {

    @Query("SELECT ri FROM RecipeImage ri where ri.recipe = :recipe")
    List<RecipeImage> findImagesByRecipe(@Param("recipe") Recipe recipe);

}
