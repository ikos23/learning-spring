package com.ivk23.recipebook.repository;

import com.ivk23.recipebook.model.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

    Set<Recipe> findAll();

}
