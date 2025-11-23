package com.example.demo;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HomEatRepository extends JpaRepository<Recipe, Long> {

    @Override
    @EntityGraph(attributePaths = "ingredients")
    List<Recipe> findAll();
}
