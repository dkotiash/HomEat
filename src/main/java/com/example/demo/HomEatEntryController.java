package com.example.demo;

import com.example.demo.dto.RecipeDto;
import com.example.demo.dto.RecipeMapper;
import com.example.demo.dto.ReviewDto;
import com.example.demo.recipe.Review;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import com.example.demo.recipe.Recipe;

@CrossOrigin(
        origins = {
                "http://localhost:5173",
                "https://frontendvue-homeat.onrender.com"
        },
        allowedHeaders = "*",
        // WICHTIG: Hier muss PUT und DELETE stehen!
        methods = {
                RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.OPTIONS
        }
)
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
        // WICHTIG: Kein RecipeMapper.toDto(...) mehr hier!
        // Der Service gibt schon das fertige DTO zurück.
        return service.updateLikes(id, increase);
    }

    @DeleteMapping("/HomEat/{id}")
    public void deleteRecipe(@PathVariable Long id) {
        service.delete(id); // Das ruft die Methode auf, die wir gerade erstellt haben
    }

    @PutMapping("/HomEat/{id}")
    public RecipeDto updateRecipe(@PathVariable Long id, @RequestBody Recipe recipeData) {
        // Der Service gibt jetzt direkt das DTO zurück.
        // Wir müssen hier NICHT mehr mappen.
        return service.update(id, recipeData);
    }

    @PostMapping("/HomEat/{id}/reviews")
    public RecipeDto addReview(@PathVariable Long id, @RequestBody ReviewDto reviewDto) {
        // Convert the incoming DTO to an Entity
        Review review = new Review(reviewDto.getText(), reviewDto.getRating(), reviewDto.getAuthorName());

        // Call the service and return the result directly.
        // DO NOT call RecipeMapper.toDto() here!
        return service.addReview(id, review);
    }


}