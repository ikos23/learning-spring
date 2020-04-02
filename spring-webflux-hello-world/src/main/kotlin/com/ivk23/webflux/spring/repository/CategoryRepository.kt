package com.ivk23.webflux.spring.repository

import com.ivk23.webflux.spring.document.Category
import org.springframework.data.mongodb.repository.ReactiveMongoRepository


interface CategoryRepository: ReactiveMongoRepository<Category, String>