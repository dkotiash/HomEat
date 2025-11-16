package com.example.demo;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {
        "http://localhost:5173",
        "https://frontendvue-homeat.onrender.com"
})
@RestController
public class HomEatEntryController {

    @GetMapping("/HomEat")
    public List<Recipe> getRecipes(){
        return List.of(
                new Recipe("Title 1", "Description 1", List.of() ),
                new Recipe("Title 1", "Description 1", List.of() ),
                new Recipe("Title 1", "Description 1", List.of() )
        );

    }
}
