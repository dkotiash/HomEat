package com.example.demo.image;

import com.example.demo.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RecipeImageRepository extends JpaRepository<RecipeImage, Long> {
    Set<RecipeImage> findByRecipe(Recipe recipe);
}
