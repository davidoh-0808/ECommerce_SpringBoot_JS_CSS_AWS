package com.application.gentlegourmet.repository;

import com.application.gentlegourmet.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {



}
