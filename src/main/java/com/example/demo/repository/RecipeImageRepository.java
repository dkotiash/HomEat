package com.example.demo.repository;

import com.example.demo.entity.Recipe;
import com.example.demo.entity.RecipeImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RecipeImageRepository extends JpaRepository<RecipeImage, Long> {
    Set<RecipeImage> findByRecipe(Recipe recipe);
}
