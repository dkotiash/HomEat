package com.example.demo.service;

import java.util.List;

import com.example.demo.repository.HomEatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.RecipeDto;
import com.example.demo.mapper.RecipeMapper;
import com.example.demo.entity.Recipe;
import com.example.demo.entity.Review;

@Service
public class HomEatService {

    @Autowired
    HomEatRepository repo;

    public HomEatService(HomEatRepository repo) {
        this.repo = repo;
    }

    public Recipe save(Recipe recipe) {
        if (recipe.getLikes() == null) {
            recipe.setLikes(0);
        }
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

    @Transactional
    public RecipeDto updateLikes(Long id, boolean increase) {
        Recipe recipe = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Rezept nicht gefunden"));

        int currentLikes = recipe.getLikes();
        if (increase) {
            recipe.setLikes(currentLikes + 1);
        } else {
            if (currentLikes > 0) recipe.setLikes(currentLikes - 1);
        }

        Recipe saved = repo.save(recipe);

        return RecipeMapper.toDto(saved);
    }

    @Transactional
    public RecipeDto update(Long id, Recipe updateData) {
        Recipe existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Rezept nicht gefunden: " + id));

        if (updateData.getTitle() != null) existing.setTitle(updateData.getTitle());
        if (updateData.getDescription() != null) existing.setDescription(updateData.getDescription());

        if (updateData.getIngredients() != null) {
            if (existing.getIngredients() == null) {
                existing.setIngredients(new java.util.ArrayList<>());
            }
            existing.getIngredients().clear();
            existing.getIngredients().addAll(updateData.getIngredients());
        }

        Recipe saved = repo.save(existing);

        return RecipeMapper.toDto(saved);
    }

    @Transactional
    public RecipeDto addReview(Long recipeId, Review review) {
        Recipe recipe = repo.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Rezept nicht gefunden"));

        recipe.addReview(review);

        Recipe saved = repo.save(recipe);

        return RecipeMapper.toDto(saved);
    }
}
