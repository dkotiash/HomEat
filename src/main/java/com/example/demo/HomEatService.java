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
    public Recipe updateRecipe(Long id, Recipe updateData) {
        // 1. Das existierende Rezept aus der Datenbank holen
        Recipe existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Rezept nicht gefunden"));

        // 2. NUR die Felder überschreiben, die man ändern darf
        existing.setTitle(updateData.getTitle());
        existing.setDescription(updateData.getDescription());

        // Zutaten aktualisieren (etwas komplexer, aber einfachste Variante:)
        if (updateData.getIngredients() != null) {
            existing.setIngredients(updateData.getIngredients());
        }

        // WICHTIG: Wir fassen 'likes', 'ownerId' und 'images' hier NICHT an.
        // Sie behalten also den Wert, den sie vorher in der Datenbank hatten.

        // 3. Speichern
        return repo.save(existing);
    }
}