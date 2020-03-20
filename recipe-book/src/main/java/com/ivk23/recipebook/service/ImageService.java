package com.ivk23.recipebook.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    void uploadImage(Long recipeId, MultipartFile image);

}
