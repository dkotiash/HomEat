package com.example.demo.dto;

import com.example.demo.recipe.Recipe;
import com.example.demo.recipe.Ingredients;
import com.example.demo.image.RecipeImage;

import java.util.List;
import java.util.stream.Collectors;

public class RecipeMapper {

    public static RecipeDto toDto(Recipe recipe) {
        List<IngredientDto> ingDtos = recipe.getIngredients() == null 
                ? List.of()
                : recipe.getIngredients().stream()
                    .map(RecipeMapper::toDto)
                    .collect(Collectors.toList());

        List<ImageResponse> imgResponses = recipe.getImages() == null
                ? List.of()
                : recipe.getImages().stream()
                    .map(RecipeMapper::toImageResponse)
                    .collect(Collectors.toList());

        return new RecipeDto(
                recipe.getId(),
                recipe.getTitle(),
                recipe.getDescription(),
                ingDtos,
                imgResponses,
                recipe.getOwnerId(),
                recipe.getLikes()// <--- NEU: Wir geben die ID an das DTO weiter
        );
    }

    private static IngredientDto toDto(Ingredients ing) {
        return new IngredientDto(ing.getName(), ing.getQuantity());
    }

    private static ImageResponse toImageResponse(RecipeImage img) {
        String url = "/api/images/" + img.getId();
        return new ImageResponse(
                img.getId(),
                url,
                safe(img.getFilename()),
                safe(img.getContentType()),
                img.getSize()
        );
    }

    private static String safe(String s) {
        return (s == null || s.isBlank()) ? "" : s;
    }
}