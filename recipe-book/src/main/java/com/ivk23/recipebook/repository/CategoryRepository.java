package com.ivk23.recipebook.repository;

import com.ivk23.recipebook.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByDescription(String description);

    Set<Category> findAll();

}
