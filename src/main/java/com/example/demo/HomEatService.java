package com.example.demo;

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
    public Recipe updateRecipe(Long id, Recipe newRecipe) {
        // SPIONAGE-LOGS: Was kommt hier eigentlich an?
        System.out.println("Update für ID: " + id);
        System.out.println("Neue Likes im Paket: " + newRecipe.getLikes());

        return repo.findById(id)
                .map(recipe -> {
                    recipe.setTitle(newRecipe.getTitle());
                    recipe.setDescription(newRecipe.getDescription());
                    recipe.setIngredients(newRecipe.getIngredients());
                    recipe.setOwnerId(newRecipe.getOwnerId());

                    // Bilder Update (Dein Code von vorhin)
                    recipe.getImages().clear();
                    if (newRecipe.getImages() != null) {
                        newRecipe.getImages().forEach(img -> img.setRecipe(recipe));
                        recipe.getImages().addAll(newRecipe.getImages());
                    }

                    // Likes Update
                    if (newRecipe.getLikes() != null) {
                        System.out.println("Setze Likes in DB auf: " + newRecipe.getLikes());
                        recipe.setLikes(newRecipe.getLikes());
                    } else {
                        System.out.println("ACHTUNG: Likes im Paket waren NULL!");
                    }

                    return repo.save(recipe);
                })
                .orElseGet(() -> {
                    newRecipe.setId(id);
                    return repo.save(newRecipe);
                });
    }
}