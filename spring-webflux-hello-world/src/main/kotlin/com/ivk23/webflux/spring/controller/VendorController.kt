package com.ivk23.webflux.spring.controller

import com.ivk23.webflux.spring.document.Vendor
import com.ivk23.webflux.spring.repository.VendorRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("vendors")
class VendorController(private val vendorRepo: VendorRepository) {

    @GetMapping
    fun getAll(): Flux<Vendor> {
        return vendorRepo.findAll()
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: String): Mono<Vendor> {
        return vendorRepo.findById(id)
    }

}