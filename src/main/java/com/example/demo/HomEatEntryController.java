package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

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
    public List<Recipe> getRecipes() {
        return service.getAll();
    }

    @PostMapping("/HomEat")
    public Recipe createRecipe(@RequestBody Recipe recipe) {
        return service.save(recipe);
    }
}