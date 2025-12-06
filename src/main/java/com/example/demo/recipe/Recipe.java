package com.example.demo.recipe;

import com.example.demo.image.RecipeImage;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Column(length = 4000)
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Ingredients> ingredients = new ArrayList<>();

    // Використовуємо Set, щоб Hibernate не бачив другу "bag"-колекцію (уникаємо MultipleBagFetchException)
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<RecipeImage> images = new LinkedHashSet<>();

    public Recipe() {}

    public Recipe(String title, String description, List<Ingredients> ingredients) {
        this.title = title;
        this.description = description;
        if (ingredients != null) this.ingredients = ingredients;
    }

    // getters/setters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<Ingredients> getIngredients() { return ingredients; }
    public void setIngredients(List<Ingredients> ingredients) { this.ingredients = ingredients; }
    public Set<RecipeImage> getImages() { return images; }
    public void setImages(Set<RecipeImage> images) { this.images = images; }
}
