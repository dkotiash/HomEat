package com.example.demo.image;

import com.example.demo.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeImageRepository extends JpaRepository<RecipeImage, Long> {
    List<RecipeImage> findByRecipe(Recipe recipe);
}
