package com.ivk23.recipebook.service;

import com.ivk23.recipebook.model.Recipe;

import java.util.Optional;
import java.util.Set;

public interface RecipeService {

    Set<Recipe> findAll();

    Optional<Recipe> findById(Long id);

    Recipe save(Recipe recipe);

}
