package com.ivk23.webflux.spring.controller

import com.ivk23.webflux.spring.document.Category
import com.ivk23.webflux.spring.repository.CategoryRepository
import org.reactivestreams.Publisher
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("categories")
class CategoryController(private val categoryRepo: CategoryRepository) {

    @GetMapping
    fun getAll(): Flux<Category> {
        return categoryRepo.findAll()
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: String): Mono<Category> {
        return categoryRepo.findById(id)
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun create(@RequestBody categoryPublisher: Publisher<Category>): Mono<Void> {
        return categoryRepo.saveAll(categoryPublisher).then()
    }

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: String,
               @RequestBody category: Category): Mono<Category> {
        category.id = id
        return categoryRepo.save(category)
    }

}