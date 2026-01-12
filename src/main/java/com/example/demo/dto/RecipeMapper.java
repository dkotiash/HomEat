package com.example.demo.dto;

import com.example.demo.recipe.Recipe;
import com.example.demo.recipe.Ingredients;
import com.example.demo.image.RecipeImage;
// Importiere deine Review Entity (Pfad ggf. anpassen, falls sie woanders liegt)
import com.example.demo.recipe.Review;
import com.example.demo.recipe.Review;

import java.util.List;
import java.util.stream.Collectors;

public class RecipeMapper {

    public static RecipeDto toDto(Recipe recipe) {
        // 1. Zutaten mappen
        List<IngredientDto> ingDtos = recipe.getIngredients() == null
                ? List.of()
                : recipe.getIngredients().stream()
                .map(RecipeMapper::toDto)
                .collect(Collectors.toList());

        // 2. Bilder mappen
        List<ImageResponse> imgResponses = recipe.getImages() == null
                ? List.of()
                : recipe.getImages().stream()
                .map(RecipeMapper::toImageResponse)
                .collect(Collectors.toList());

        // 3. --- NEU: Bewertungen mappen ---
        // Wir wandeln jede "Review"-Entity in ein "ReviewDto" um
        List<ReviewDto> reviewDtos = recipe.getReviews() == null
                ? List.of()
                : recipe.getReviews().stream()
                .map(review -> new ReviewDto(
                        review.getText(),
                        review.getRating(),
                        review.getAuthorName()
                ))
                .collect(Collectors.toList());
        // ----------------------------------

        // 4. DTO zurückgeben
        return new RecipeDto(
                recipe.getId(),
                recipe.getTitle(),
                recipe.getDescription(),
                ingDtos,
                imgResponses,
                recipe.getOwnerId(),
                recipe.getLikes(), // Likes
                reviewDtos         // <--- NEU: Die Liste der Bewertungen hier übergeben!
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