package com.ivk23.recipebook.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    private static Category classUnderTest;

    @BeforeAll
    static void init() {
        classUnderTest = new Category();
        classUnderTest.setId(123L);
        classUnderTest.setDescription("Test category");
        classUnderTest.setRecipes(Set.of(new Recipe()));
    }

    @Test
    void setIdTest() {
        final var category = new Category();
        category.setId(777L);
        assertEquals(777L, category.getId());
    }

    @Test
    void setDescriptionTest() {
        final var category = new Category();
        category.setDescription("lol");
        assertEquals("lol", category.getDescription());
    }

    @Test
    void getIdTest() {
        assertEquals(123L, classUnderTest.getId());
    }

    @Test
    void getDescriptionTest() {
        assertEquals("Test category", classUnderTest.getDescription());
    }

    @Test
    void getRecipesTest() {
        assertEquals(1, classUnderTest.getRecipes().size());
    }
}