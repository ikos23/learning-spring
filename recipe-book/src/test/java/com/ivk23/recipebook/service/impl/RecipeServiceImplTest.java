package com.ivk23.recipebook.service.impl;

import com.ivk23.recipebook.model.Recipe;
import com.ivk23.recipebook.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    @Mock
    RecipeRepository repository;

    RecipeServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new RecipeServiceImpl(repository);
    }

    @Test
    void findById() {
        Recipe test = new Recipe();
        test.setCookTime(123);
        test.setId(1L);
        test.setDescription("Test recipe");

        when(repository.findById(1L)).thenReturn(Optional.of(test));

        final var recipe = service.findById(1l);
        assertEquals("Test recipe", recipe.get().getDescription());
        assertEquals(123, recipe.get().getCookTime());

        verify(repository).findById(1L);
    }
}