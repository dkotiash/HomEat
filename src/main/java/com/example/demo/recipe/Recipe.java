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
    // --- NEU: Hier speichern wir, wem das Rezept gehört ---
    private String ownerId;
    // -----------------------------------------------------

    // ... deine Zutaten und Bilder Listen ...

    // --- WICHTIG: Getter und Setter für ownerId nicht vergessen! ---
    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Ingredients> ingredients = new ArrayList<>();

    // Використовуємо Set, щоб Hibernate не бачив другу "bag"-колекцію (уникаємо MultipleBagFetchException)
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<RecipeImage> images = new LinkedHashSet<>();

    public Recipe() {}

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
