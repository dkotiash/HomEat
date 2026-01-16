package com.example.demo.mapper;

import com.example.demo.dto.ImageResponse;
import com.example.demo.dto.IngredientDto;
import com.example.demo.dto.RecipeDto;
import com.example.demo.dto.ReviewDto;
import com.example.demo.entity.Recipe;
import com.example.demo.entity.Ingredients;
import com.example.demo.entity.RecipeImage;
// Importiere deine Review Entity (Pfad ggf. anpassen, falls sie woanders liegt)

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


        List<ReviewDto> reviewDtos = recipe.getReviews() == null
                ? List.of()
                : recipe.getReviews().stream()
                .map(review -> new ReviewDto(
                        review.getText(),
                        review.getRating(),
                        review.getAuthorName()
                ))
                .collect(Collectors.toList());

        return new RecipeDto(
                recipe.getId(),
                recipe.getTitle(),
                recipe.getDescription(),
                ingDtos,
                imgResponses,
                recipe.getOwnerId(),
                recipe.getLikes(),
                reviewDtos
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