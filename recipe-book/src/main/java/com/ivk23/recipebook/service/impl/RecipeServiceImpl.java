package com.ivk23.recipebook.service.impl;

import com.ivk23.recipebook.model.Recipe;
import com.ivk23.recipebook.repository.RecipeRepository;
import com.ivk23.recipebook.service.RecipeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> findAll() {
        return this.recipeRepository.findAll();
    }

    @Override
    public Optional<Recipe> findById(Long id) {
        return this.recipeRepository.findById(id);
    }

    @Override
    @Transactional
    public Recipe save(Recipe recipe) {
        return this.recipeRepository.save(recipe);
    }
}
