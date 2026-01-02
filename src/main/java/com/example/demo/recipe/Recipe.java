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

    private String ownerId;

    // --- NEU: HIER DAS FELD FÜR LIKES HINZUFÜGEN ---
    // Wir setzen es standardmäßig auf 0, damit es nicht null ist
    private Integer likes = 0;
    // -----------------------------------------------

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Ingredients> ingredients = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<RecipeImage> images = new LinkedHashSet<>();

    public Recipe() {}

    // --- NEU: GETTER UND SETTER FÜR LIKES NICHT VERGESSEN ---
    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }
    // --------------------------------------------------------

    // ... deine existierenden Getter/Setter ...
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }
    public void setId(Long id) {
        this.id = id;
    }
    public List<Ingredients> getIngredients() { return ingredients; }
    public void setIngredients(List<Ingredients> ingredients) { this.ingredients = ingredients; }
    public Set<RecipeImage> getImages() { return images; }
    public void setImages(Set<RecipeImage> images) { this.images = images; }
}