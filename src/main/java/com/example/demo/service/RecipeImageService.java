package com.example.demo.service;

import com.example.demo.repository.HomEatRepository;        // репозиторій для Recipe
import com.example.demo.entity.Recipe;            // твоя сутність Recipe
import com.example.demo.entity.RecipeImage;
import com.example.demo.repository.RecipeImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
public class RecipeImageService {

    private final RecipeImageRepository imageRepo;
    private final HomEatRepository recipeRepo;

    public RecipeImageService(RecipeImageRepository imageRepo, HomEatRepository recipeRepo) {
        this.imageRepo = imageRepo;
        this.recipeRepo = recipeRepo;
    }

    @Transactional
    public RecipeImage save(MultipartFile file) {
        return imageRepo.save(toEntity(null, file));
    }

    @Transactional
    public RecipeImage saveForRecipe(Long recipeId, MultipartFile file) {
        Recipe recipe = recipeRepo.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found: " + recipeId));
        return imageRepo.save(toEntity(recipe, file));
    }

    @Transactional(readOnly = true)
    public RecipeImage get(Long id) {
        return imageRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found: " + id));
    }

    @Transactional
    public void delete(Long id) {
        imageRepo.deleteById(id);
    }

    private RecipeImage toEntity(Recipe recipe, MultipartFile file) {
        try {
            RecipeImage img = new RecipeImage();
            img.setFilename(file.getOriginalFilename());
            img.setContentType(file.getContentType());
            img.setSize(file.getSize());
            img.setData(file.getBytes());
            img.setRecipe(recipe);
            return img;
        } catch (IOException e) {
            throw new RuntimeException("Cannot read uploaded file bytes", e);
        }
    }
}
