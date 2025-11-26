package com.example.demo.recipe;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.image.RecipeImage;  // <-- важливо

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Ingredients> ingredients = new ArrayList<>();

    // зв’язок з картинками
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeImage> images = new ArrayList<>();

    public Recipe() {}

    public Recipe(String title, String description, List<Ingredients> ingredients) {
        this.title = title;
        this.description = description;
        if (ingredients != null) this.ingredients = ingredients;
    }

    // --- гетери/сетери ---
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<Ingredients> getIngredients() { return ingredients; }
    public void setIngredients(List<Ingredients> ingredients) { this.ingredients = ingredients; }

    public List<RecipeImage> getImages() { return images; }
    public void setImages(List<RecipeImage> images) { this.images = images; }
}
