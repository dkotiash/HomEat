package com.example.demo;

import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.recipe.Ingredients;
import com.example.demo.recipe.Recipe;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class HomEatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HomEatRepository repository;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    // ========== HAPPY PATH TESTS ==========

    @Test
    void shouldReturnAllRecipes() throws Exception {
        // given
        Recipe recipe1 = createTestRecipe("Pasta Carbonara", "Italian classic");
        Recipe recipe2 = createTestRecipe("Pizza Margherita", "Simple pizza");
        repository.save(recipe1);
        repository.save(recipe2);

        // when & then
        mockMvc.perform(get("/HomEat"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].title", hasItem("Pasta Carbonara")))
                .andExpect(jsonPath("$[*].title", hasItem("Pizza Margherita")));
    }

    @Test
    void shouldCreateRecipeSuccessfully() throws Exception {
        // given
        Recipe newRecipe = createTestRecipe("Caesar Salad", "Fresh and healthy");
        String recipeJson = objectMapper.writeValueAsString(newRecipe);

        // when & then
        mockMvc.perform(post("/HomEat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(recipeJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Caesar Salad"))
                .andExpect(jsonPath("$.description").value("Fresh and healthy"))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void shouldUpdateRecipeSuccessfully() throws Exception {
        // given
        Recipe existingRecipe = createTestRecipe("Old Title", "Old Description");
        Recipe savedRecipe = repository.save(existingRecipe);

        Recipe updatedRecipe = new Recipe();
        updatedRecipe.setTitle("Updated Title");
        updatedRecipe.setDescription("Updated Description");
        updatedRecipe.setIngredients(List.of(new Ingredients("Salt", "1 tsp")));
        updatedRecipe.setLikes(5);

        String updatedJson = objectMapper.writeValueAsString(updatedRecipe);

        // when & then
        mockMvc.perform(put("/HomEat/" + savedRecipe.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.description").value("Updated Description"))
                .andExpect(jsonPath("$.likes").value(5));
    }

    @Test
    void shouldDeleteRecipeSuccessfully() throws Exception {
        // given
        Recipe recipe = createTestRecipe("To Delete", "Will be removed");
        Recipe savedRecipe = repository.save(recipe);

        // when & then
        mockMvc.perform(delete("/HomEat/" + savedRecipe.getId()))
                .andExpect(status().isOk());

        // verify deletion
        mockMvc.perform(get("/HomEat"))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldReturnRecipeWithMultipleIngredients() throws Exception {
        // given
        Recipe recipe = createTestRecipe("Spaghetti Bolognese", "Classic Italian");
        recipe.setIngredients(List.of(
                new Ingredients("Spaghetti", "500g"),
                new Ingredients("Ground Beef", "300g"),
                new Ingredients("Tomato Sauce", "400ml")
        ));
        repository.save(recipe);

        // when & then
        mockMvc.perform(get("/HomEat"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ingredients", hasSize(3)))
                .andExpect(jsonPath("$[0].ingredients[0].name").value("Spaghetti"))
                .andExpect(jsonPath("$[0].ingredients[1].name").value("Ground Beef"));
    }

    // ========== SAD PATH TESTS ==========

    @Test
    void shouldReturnEmptyListWhenNoRecipesExist() throws Exception {
        // given - empty database
        repository.deleteAll();

        // when & then
        mockMvc.perform(get("/HomEat"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldCreateRecipeEvenWithNonExistentId() throws Exception {
        // given
        Recipe recipe = createTestRecipe("Ghost Recipe", "Created from nowhere");
        String recipeJson = objectMapper.writeValueAsString(recipe);

        // when & then
        mockMvc.perform(put("/HomEat/99999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(recipeJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Ghost Recipe"));
    }

    @Test
    void shouldHandleDeleteOfNonExistentRecipe() throws Exception {
        // when & then
        mockMvc.perform(delete("/HomEat/99999"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldHandleRecipeWithEmptyIngredients() throws Exception {
        // given
        Recipe recipe = createTestRecipe("Simple Recipe", "No ingredients needed");
        recipe.setIngredients(List.of());
        String recipeJson = objectMapper.writeValueAsString(recipe);

        // when & then
        mockMvc.perform(post("/HomEat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(recipeJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ingredients", hasSize(0)));
    }

    @Test
    void shouldHandleRecipeWithNullLikes() throws Exception {
        // given
        Recipe recipe = createTestRecipe("No Likes Yet", "Brand new recipe");
        recipe.setLikes(null);
        String recipeJson = objectMapper.writeValueAsString(recipe);

        // when & then
        mockMvc.perform(post("/HomEat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(recipeJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.likes").value(0));
    }

    // ========== HELPER METHOD ==========

    private Recipe createTestRecipe(String title, String description) {
        Recipe recipe = new Recipe();
        recipe.setTitle(title);
        recipe.setDescription(description);
        recipe.setOwnerId("test-user-123");
        recipe.setLikes(0);
        recipe.setIngredients(List.of(new Ingredients("Base Ingredient", "1 unit")));
        return recipe;
    }
}
