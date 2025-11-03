package com.example.demo;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class HomEatEntryController {

    @GetMapping("/todos1")
    public List<HomEatEntry> getTodos(){
        return List.of(
                new HomEatEntry("Title 1", "Description 1"),
                new HomEatEntry("Title 2", "Description 2"),
                new HomEatEntry("Title 3", "Description 3")
        );

    }
}
