package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.recipe.Recipe;

import java.util.List;

@Service
public class HomEatService {

    @Autowired
    HomEatRepository repo;

    public HomEatService(HomEatRepository repo) {
        this.repo = repo;
    }

    public Recipe save(Recipe recipe) {
        return repo.save(recipe);
    }

    public Recipe get(Long id) {
        return repo.findById(id).orElseThrow(RuntimeException::new);
    }
    public List<Recipe> getAll() {
        return repo.findAll();
    }
    public void delete(Long id) {
        repo.deleteById(id);
    }

}
