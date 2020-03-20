package com.ivk23.recipebook.controller;

import com.ivk23.recipebook.model.Recipe;
import com.ivk23.recipebook.service.ImageService;
import com.ivk23.recipebook.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    private RecipeController classUnderTest;

    @Mock
    private RecipeService recipeService;

    @Mock
    private ImageService imageService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        classUnderTest = new RecipeController(recipeService, imageService);
        mockMvc = MockMvcBuilders.standaloneSetup(classUnderTest).build();
    }

    @Test
    void addPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("addRecipe"));
    }

    @Test
    void uploadPageSuccess() throws Exception {
        var testRecipe = new Recipe();
        testRecipe.setId(123L);

        when(recipeService.findById(123L)).thenReturn(Optional.of(testRecipe));

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/123/upload"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("uploadImg"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));

        verify(recipeService).findById(123L);
    }

    @Test
    void uploadPageRecipeNotFound() throws Exception {
        when(recipeService.findById(123L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/123/upload"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        verify(recipeService).findById(123L);
    }

    @Test
    void upload() {
    }

    @Test
    void create() {
    }

    @Test
    void showImg() {
    }
}