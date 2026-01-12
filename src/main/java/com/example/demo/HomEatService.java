package com.example.demo;

import com.example.demo.dto.RecipeDto;
import com.example.demo.dto.RecipeMapper;
import com.example.demo.recipe.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
// 1. ADD THIS IMPORT
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.recipe.Recipe;

import java.util.List;

@Service
public class HomEatService {

    @Autowired
    HomEatRepository repo;

    public HomEatService(HomEatRepository repo) {
        this.repo = repo;
    }

    public Recipe save(Recipe recipe) {
        return repo.save(recipe);
    }

    public Recipe get(Long id) {
        return repo.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<Recipe> getAll() {
        return repo.findAll();
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    // 2. ADD THIS ANNOTATION
    @Transactional
    public Recipe updateLikes(Long id, boolean increase) {
        Recipe recipe = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Rezept nicht gefunden"));

        int currentLikes = recipe.getLikes();

        if (increase) {
            // Erhöhen
            recipe.setLikes(currentLikes + 1);
        } else {
            // Verringern (aber nicht unter 0 gehen)
            if (currentLikes > 0) {
                recipe.setLikes(currentLikes - 1);
            }
        }

        return repo.save(recipe);
    }

    @Transactional
    public RecipeDto update(Long id, Recipe updateData) {
        Recipe existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Rezept nicht gefunden: " + id));

        // 1. Textfelder Update
        if (updateData.getTitle() != null) existing.setTitle(updateData.getTitle());
        if (updateData.getDescription() != null) existing.setDescription(updateData.getDescription());

        // 2. Zutaten Update
        if (updateData.getIngredients() != null) {
            if (existing.getIngredients() == null) {
                existing.setIngredients(new java.util.ArrayList<>());
            }
            existing.getIngredients().clear();
            existing.getIngredients().addAll(updateData.getIngredients());
        }

        // 3. Speichern
        Recipe saved = repo.save(existing);

        // 4. HIER wandeln wir um, solange die Transaktion noch offen ist!
        return RecipeMapper.toDto(saved);
    }


    public Recipe addReview(Long recipeId, Review review) {
        Recipe recipe = repo.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Rezept nicht gefunden"));

        // Review verknüpfen
        recipe.addReview(review);

        return repo.save(recipe);
    }
}