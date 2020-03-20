package com.ivk23.recipebook.controller;

import com.ivk23.recipebook.exception.ResourceNotFound;
import com.ivk23.recipebook.model.Recipe;
import com.ivk23.recipebook.service.ImageService;
import com.ivk23.recipebook.service.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/recipe")
@AllArgsConstructor
public class RecipeController {

    final RecipeService recipeService;
    final ImageService imageService;

    @GetMapping("/add")
    public String addPage(Model model) {
        return "addRecipe";
    }

    @GetMapping("/{id}/upload")
    public String uploadPage(@PathVariable Long id, Model model) {
        final var recipe = recipeService.findById(id);

        if (recipe.isEmpty()) {
            throw new ResourceNotFound("Requested recipe cannot be found.");
        }

        model.addAttribute("recipe", recipe.get());
        return "uploadImg";
    }

    @PostMapping("/{id}/upload")
    public String upload(@PathVariable Long id, @RequestParam("theImage") MultipartFile image) {
        this.imageService.uploadImage(id, image);
        return "redirect:/index";
    }

    @PostMapping("/add")
    public ModelAndView create(Recipe recipe) {
        System.out.println("NEW RECIPE: " + recipe);
        this.recipeService.save(recipe);
        return new ModelAndView("redirect:/index");
    }

    @GetMapping("/{recipeId}/image")
    public void showImg(@PathVariable Long recipeId, HttpServletResponse response) {
        final var recipeOptional = this.recipeService.findById(recipeId);
        if (recipeOptional.isEmpty()) {
            throw new IllegalStateException("No recipe found for id=" + recipeId);
        }

        final var image = recipeOptional.get().getImage();
        final byte[] bytes = new byte[image.length];
        for (int i=0; i < image.length; i++) {
            bytes[i] = image[i];
        }

        response.setContentType("image/jpeg");
        try {
            response.getOutputStream().write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormatException(Exception ex) {
        ModelAndView errorView = new ModelAndView("error");

        String message = ex.getMessage();
        if (ex instanceof MethodArgumentTypeMismatchException) {
            message = "Are you stupid ? [" + ((MethodArgumentTypeMismatchException) ex).getValue() + "] is not a number";
        }
        errorView.addObject("status", 400);
        errorView.addObject("message", message);
        errorView.addObject("path", "???");

        return errorView;
    }

}
