package com.example.demo.recipe;

import jakarta.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

import com.example.demo.image.RecipeImage;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "recipe_ingredients",
            joinColumns = @JoinColumn(name = "recipe_id"))
    private Set<Ingredients> ingredients = new LinkedHashSet<>();

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<RecipeImage> images = new LinkedHashSet<>();

    public Recipe() {}

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Set<Ingredients> getIngredients() { return ingredients; }
    public void setIngredients(Set<Ingredients> ingredients) { this.ingredients = ingredients; }

    public Set<RecipeImage> getImages() { return images; }
    public void setImages(Set<RecipeImage> images) { this.images = images; }
}
