package com.example.demo;

import com.example.demo.recipe.Recipe;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomEatRepository extends JpaRepository<Recipe, Long> {

    @Override
    @EntityGraph(attributePaths = { "ingredients", "images" })  // ← критично
    List<Recipe> findAll();
}
