package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ToDoEntryController {

    @GetMapping("/todos1")
    public List<ToDoEntry> getTodos(){
        return List.of(
                new ToDoEntry("Title 1", "Description 1"),
                new ToDoEntry("Title 2", "Description 2"),
                new ToDoEntry("Title 3", "Description 3")
        );

    }
}
