package com.ivk23.recipebook.repository.reactive;

import com.ivk23.recipebook.model.RecipeDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ReactiveRecipeDocumentRepository extends ReactiveMongoRepository<RecipeDocument, Long> {

}
