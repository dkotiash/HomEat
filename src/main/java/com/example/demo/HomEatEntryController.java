package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<Recipe> getRecipes(){
        return List.of(
                new Recipe("Title 1", "Description 1", List.of() ),
                new Recipe("Title 1", "Description 1", List.of() ),
                new Recipe("Title 1", "Description 1", List.of() )
        );

    }

    @CrossOrigin
    @PostMapping("/things")
    public Recipe createRecipe(@RequestBody Recipe recipe) {
        return service.save(recipe);
    }
}
