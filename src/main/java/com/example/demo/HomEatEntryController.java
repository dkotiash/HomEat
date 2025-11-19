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

    @Autowired
    HomEatService service;

    @GetMapping("/HomEat")
    public List<Recipe> getRecipes() {
        return service.getAll();
    }

    @CrossOrigin
    @PostMapping("/HomEat")
    public Recipe createRecipe(@RequestBody Recipe recipe) {
        return service.save(recipe);
    }
}
