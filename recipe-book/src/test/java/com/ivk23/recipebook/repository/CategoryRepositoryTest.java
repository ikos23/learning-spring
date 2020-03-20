package com.ivk23.recipebook.repository;

import com.ivk23.recipebook.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void findAllTest() {
        final var categories = categoryRepository.findAll();
        assertEquals(4, categories.size());
    }

    @Test
    void findByDescriptionTest() {
        final var categories = categoryRepository.findByDescription("Fast Food");
        assertTrue(categories.isPresent());
        assertEquals("Fast Food", categories.get().getDescription());
    }

}
