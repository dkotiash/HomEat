package com.example.demo.image;

import com.example.demo.HomEatRepository;        // репозиторій для Recipe
import com.example.demo.recipe.Recipe;            // твоя сутність Recipe
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

    // ===== ВИКЛИКАЄТЬСЯ КОНТРОЛЕРОМ: imageService.save(file)
    @Transactional
    public RecipeImage save(MultipartFile file) {
        return imageRepo.save(toEntity(null, file));
    }

    // ===== ВИКЛИКАЄТЬСЯ КОНТРОЛЕРОМ: imageService.saveForRecipe(recipeId, file)
    @Transactional
    public RecipeImage saveForRecipe(Long recipeId, MultipartFile file) {
        Recipe recipe = recipeRepo.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found: " + recipeId));
        return imageRepo.save(toEntity(recipe, file));
    }

    // ===== Підвантаження байтів для GET /api/images/{id}
    @Transactional(readOnly = true)
    public RecipeImage get(Long id) {
        return imageRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found: " + id));
    }

    // ===== Видалення
    @Transactional
    public void delete(Long id) {
        imageRepo.deleteById(id);
    }

    // ===== Мапер Multipart -> Entity
    private RecipeImage toEntity(Recipe recipe, MultipartFile file) {
        try {
            RecipeImage img = new RecipeImage();
            img.setFilename(file.getOriginalFilename());
            img.setContentType(file.getContentType());
            img.setSize(file.getSize());
            img.setData(file.getBytes());
            img.setRecipe(recipe);       // може бути null для "просто збереження"
            return img;
        } catch (IOException e) {
            throw new RuntimeException("Cannot read uploaded file bytes", e);
        }
    }
}
