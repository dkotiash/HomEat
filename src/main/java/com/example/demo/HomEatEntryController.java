package com.example.demo;

import com.example.demo.dto.RecipeDto;
import com.example.demo.dto.RecipeMapper;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import com.example.demo.recipe.Recipe;

@CrossOrigin(origins = {
        "http://localhost:5173",
        "https://frontendvue-homeat.onrender.com"
})
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

    @PutMapping("/HomEat/{id}")
    public Recipe updateRecipe(@PathVariable Long id, @RequestBody Recipe recipe) {
        // Wir geben die Arbeit an den Service weiter
        return service.updateRecipe(id, recipe);
    }

    @DeleteMapping("/HomEat/{id}")
    public void deleteRecipe(@PathVariable Long id) {
        service.delete(id); // Das ruft die Methode auf, die wir gerade erstellt haben
    }

}