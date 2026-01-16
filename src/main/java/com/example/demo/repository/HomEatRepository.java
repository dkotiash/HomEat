package com.example.demo.repository;

import com.example.demo.entity.Recipe;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomEatRepository extends JpaRepository<Recipe, Long> {
    @Override
    @EntityGraph(attributePaths = {"ingredients", "images"})
    List<Recipe> findAll();
}

