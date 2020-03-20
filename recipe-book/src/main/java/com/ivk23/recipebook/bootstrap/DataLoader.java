package com.ivk23.recipebook.bootstrap;

import com.ivk23.recipebook.model.Difficulty;
import com.ivk23.recipebook.model.Note;
import com.ivk23.recipebook.model.Recipe;
import com.ivk23.recipebook.model.RecipeDocument;
import com.ivk23.recipebook.repository.RecipeDocumentRepository;
import com.ivk23.recipebook.repository.reactive.ReactiveRecipeDocumentRepository;
import com.ivk23.recipebook.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeDocumentRepository recipeDocumentRepository;

    @Autowired
    ReactiveRecipeDocumentRepository reactiveRecipeDocumentRepository;

    @Override
    public void run(String... args) throws Exception {
        final var note = new Note();
        note.setNote("test note");

        final var recipe = new Recipe();
        recipe.setDescription("Test recipe");
        recipe.setCookTime(12);
        recipe.setPrepTime(25);
        recipe.setDifficulty(Difficulty.MODERATE);
        recipe.setNote(note);
        recipe.setServings(5);
        note.setRecipe(recipe);
        recipeService.save(recipe);

        var recipeDocument = new RecipeDocument();
        recipeDocument.setDescription("Test recipe");
        recipeDocument.setCookTime(12);
        recipeDocument.setPrepTime(25);
        recipeDocument.setDifficulty(Difficulty.MODERATE);
        recipeDocumentRepository.save(recipeDocument);

        System.out.println("Test Data Loaded !");

        reactiveRecipeDocumentRepository.count();
    }
}
