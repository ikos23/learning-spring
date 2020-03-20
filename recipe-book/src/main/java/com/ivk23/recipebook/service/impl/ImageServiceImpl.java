package com.ivk23.recipebook.service.impl;

import com.ivk23.recipebook.repository.RecipeRepository;
import com.ivk23.recipebook.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@Service
public class ImageServiceImpl implements ImageService {

    final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Transactional
    @Override
    public void uploadImage(Long recipeId, MultipartFile image) {
        try {
            final var recipeOptional = this.recipeRepository.findById(recipeId);
            if (recipeOptional.isEmpty()) {
                throw new IllegalStateException("Cannot find recipeOptional by id=" + recipeId);
            }

            final var imgBytes = image.getBytes();
            final Byte[] fileData = new Byte[imgBytes.length];
            Arrays.setAll(fileData, i -> imgBytes[i]);

            final var recipe = recipeOptional.get();
            recipe.setImage(fileData);
            this.recipeRepository.save(recipe);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
