package com.ivk23.webflux.spring.controller

import com.ivk23.webflux.spring.document.Category
import com.ivk23.webflux.spring.repository.CategoryRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.reactivestreams.Publisher
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@ExtendWith(MockitoExtension::class)
class CategoryControllerTest {

    private lateinit var webTestClient: WebTestClient

    @Mock
    private lateinit var categoryRepository: CategoryRepository
    private lateinit var categoryController: CategoryController

    @BeforeEach
    fun setUp() {
        categoryController = CategoryController(categoryRepository)
        webTestClient = WebTestClient.bindToController(categoryController).build()
    }

    @Test
    fun getAll() {
        val testCat = Category().apply { description = "TestCategory" }
        val testCat2 = Category().apply { description = "TestCategory2" }
        `when`(categoryRepository.findAll()).thenReturn(Flux.just(testCat, testCat2))

        webTestClient.get()
                .uri("/categories")
                .exchange()
                .expectBodyList(Category::class.java)
                .hasSize(2)

        verify(categoryRepository).findAll()

    }

    @Test
    fun getById() {
        val testCat = Category().apply { description = "TestCategory1" }

        `when`(categoryRepository.findById("123")).thenReturn(Mono.just(testCat))

        webTestClient.get()
                .uri("/categories/123")
                .exchange()
                .expectBody(Category::class.java)


        verify(categoryRepository).findById("123")
    }

    @Test
    fun create() {
        val milk = Category().apply { description = "TestMilk" }

        `when`(categoryRepository.saveAll(any<Publisher<Category>>()))
                .thenReturn(Flux.just(milk))

        webTestClient.post()
                .uri("/categories")
                .body(Mono.just(milk), Category::class.java)
                .exchange()
                .expectStatus()
                .isCreated


        verify(categoryRepository).saveAll(any<Publisher<Category>>())
    }

    @Test
    fun update() {
        val milk = Category().apply { description = "TestMilk" }

        `when`(categoryRepository.save(any(Category::class.java)))
                .thenReturn(Mono.just(milk))

        webTestClient.put()
                .uri("/categories/1234")
                .body(Mono.just(milk), Category::class.java)
                .exchange()
                .expectStatus()
                .isOk

        verify(categoryRepository).save(any(Category::class.java))
    }
}