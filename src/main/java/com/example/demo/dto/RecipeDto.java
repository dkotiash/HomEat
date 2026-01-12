package com.example.demo.dto;

import java.util.List;

public class RecipeDto {
    private Long id;
    private String title;
    private String description;
    private List<IngredientDto> ingredients;
    private List<ImageResponse> images;
    private String ownerId;

    // --- NEU: Diese Felder müssen da sein ---
    private int likes;
    private List<ReviewDto> reviews;
    // ---------------------------------------

    // --- WICHTIG: Der Konstruktor muss GENAU zur Aufruf-Reihenfolge passen ---
    public RecipeDto(Long id, String title, String description,
                     List<IngredientDto> ingredients, List<ImageResponse> images,
                     String ownerId, int likes, List<ReviewDto> reviews) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.images = images;
        this.ownerId = ownerId;
        this.likes = likes;
        this.reviews = reviews;
    }

    // --- Getter und Setter ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<IngredientDto> getIngredients() { return ingredients; }
    public void setIngredients(List<IngredientDto> ingredients) { this.ingredients = ingredients; }

    public List<ImageResponse> getImages() { return images; }
    public void setImages(List<ImageResponse> images) { this.images = images; }

    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }

    // --- Getter/Setter für die neuen Felder nicht vergessen! ---
    public int getLikes() { return likes; }
    public void setLikes(int likes) { this.likes = likes; }

    public List<ReviewDto> getReviews() { return reviews; }
    public void setReviews(List<ReviewDto> reviews) { this.reviews = reviews; }
}