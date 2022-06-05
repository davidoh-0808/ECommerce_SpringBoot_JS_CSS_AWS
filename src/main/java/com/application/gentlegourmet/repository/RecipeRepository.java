package com.application.gentlegourmet.repository;

import com.application.gentlegourmet.entity.Recipe;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @EntityGraph(
        value = "recipe-graph.name-product",
        type = EntityGraph.EntityGraphType.LOAD
    )
    @Query("SELECT r FROM Recipe r")
    Set<Recipe> findAllRecipesNameAndProductOnly();


    @EntityGraph(
        value = "recipe-graph.all-fields-except-details",
        type = EntityGraph.EntityGraphType.LOAD
    )
    @Query("SELECT r FROM Recipe r")
    Set<Recipe> findAllRecipesAllFieldsExceptDetails();

}
