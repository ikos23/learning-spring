package com.ivk23.webflux.spring.repository

import com.ivk23.webflux.spring.document.Vendor
import org.springframework.data.mongodb.repository.ReactiveMongoRepository


interface VendorRepository : ReactiveMongoRepository<Vendor, String>