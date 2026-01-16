package com.example.demo.controller;

import com.example.demo.service.HomEatService;
import com.example.demo.dto.RecipeDto;
import com.example.demo.mapper.RecipeMapper;
import com.example.demo.dto.ReviewDto;
import com.example.demo.entity.Review;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import com.example.demo.entity.Recipe;

@CrossOrigin(origins = {"http://localhost:5173", "https://frontendvue-homeat.onrender.com"}, allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
@RestController
public class HomEatEntryController {

    private final HomEatService service;

    public HomEatEntryController(HomEatService service) {
        this.service = service;
    }

    @GetMapping("/HomEat")
    public List<RecipeDto> getRecipes() {
        return service.getAll().stream()
                .map(RecipeMapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/HomEat")
    public Recipe createRecipe(@RequestBody Recipe recipe) {
        return service.save(recipe);
    }

    @PostMapping("/HomEat/{id}/like")
    public RecipeDto updateLikes(@PathVariable Long id, @RequestParam boolean increase) {
        return service.updateLikes(id, increase);
    }

    @DeleteMapping("/HomEat/{id}")
    public void deleteRecipe(@PathVariable Long id) {
        service.delete(id);
    }

    @PutMapping("/HomEat/{id}")
    public RecipeDto updateRecipe(@PathVariable Long id, @RequestBody Recipe recipeData) {
        return service.update(id, recipeData);
    }

    @PostMapping("/HomEat/{id}/reviews")
    public RecipeDto addReview(@PathVariable Long id, @RequestBody ReviewDto reviewDto) {
        Review review = new Review(reviewDto.getText(), reviewDto.getRating(), reviewDto.getAuthorName());
        return service.addReview(id, review);
    }


}