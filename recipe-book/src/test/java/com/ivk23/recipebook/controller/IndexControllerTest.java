package com.ivk23.recipebook.controller;

import com.ivk23.recipebook.model.Recipe;
import com.ivk23.recipebook.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IndexControllerTest {

    @Mock
    private Model model;

    @Mock
    private RecipeService recipeService;

    private IndexController classUnderTest;

    @BeforeEach
    void init() {
        classUnderTest = new IndexController(recipeService);
    }

    @Test
    void indexTest() {
        when(this.recipeService.findAll()).thenReturn(Set.of(new Recipe()));
        when(this.model.addAttribute(eq("recipes"), any(Set.class))).thenReturn(this.model);

        final var res = classUnderTest.index(model);
        assertEquals("index", res);

        verify(recipeService).findAll();
        verify(model).addAttribute(eq("recipes"), any(Set.class));
    }
}