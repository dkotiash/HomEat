package com.example.demo.dto;

import java.util.List;

public class RecipeDto {
    private Long id;
    private String title;
    private String description;
    private List<IngredientDto> ingredients;
    private List<ImageResponse> images; // <-- ImageResponse nutzen

    public RecipeDto() {}

    public RecipeDto(Long id, String title, String description, 
                     List<IngredientDto> ingredients, List<ImageResponse> images) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.ingredients = ingredients;
        this.images = images;
    }

    // Getter/Setter
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public List<IngredientDto> getIngredients() { return ingredients; }
    public List<ImageResponse> getImages() { return images; }

    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setIngredients(List<IngredientDto> ingredients) { this.ingredients = ingredients; }
    public void setImages(List<ImageResponse> images) { this.images = images; }
}