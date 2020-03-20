package com.ivk23.petclinic.kotlin.api

import com.ivk23.petclinic.kotlin.exception.ResourceNotFoundException
import com.ivk23.petclinic.kotlin.model.Pet
import com.ivk23.petclinic.kotlin.repository.OwnerRepository
import com.ivk23.petclinic.kotlin.repository.PetRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort.Order.asc
import org.springframework.data.domain.Sort.by
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.constraints.Max
import javax.validation.constraints.Min

@Validated
@RestController
@RequestMapping("/api/pets")
class PetResource(private val petRepo: PetRepository,
                  private val ownerRepo: OwnerRepository) {

    companion object {
        val log: Logger = LoggerFactory
                .getLogger(PetResource::class.java)
    }

    @GetMapping
    fun get(@RequestParam(name = "page", defaultValue = "1")
            @Min(1)
            page: Int,
            @RequestParam(name = "size", defaultValue = "100")
            @Min(1)
            @Max(100)
            size: Int): List<Pet> {
        return petRepo
                .findAll(PageRequest.of(page - 1, size, by(asc("name"))))
                .toList() // if you do toSet() here you can loose sorting :)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") petId: Long): Pet {
        return petRepo
                .findById(petId)
                .orElseThrow {
                    log.debug("Pet (id=$petId) not found.")
                    ResourceNotFoundException("Pet (id=$petId) not found.")
                }
    }

    @Transactional
    @PostMapping
    @ResponseStatus(CREATED)
    fun create(@Validated @RequestBody pet: Pet) {
        if (ownerRepo.findById(pet.ownerId!!).isEmpty) {
            throw ResourceNotFoundException("Owner (id=${pet.ownerId}) not found.")
        }

        petRepo.save(pet)
    }

    @Transactional
    @PutMapping
    fun update(@Validated @RequestBody pet: Pet) {
        if (ownerRepo.findById(pet.ownerId!!).isEmpty) {
            throw ResourceNotFoundException("Owner (id=${pet.ownerId}) not found.")
        }

        val perhapsPet =
                if (pet.id != null) petRepo.findById(pet.id!!)
                else Optional.empty()

        perhapsPet.ifPresentOrElse(
                { petRepo.save(pet) },
                { throw ResourceNotFoundException("Pet (id=${pet.id}) not found.") })
    }

    @Transactional
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    fun delete(@PathVariable("id") petId: Long) {
        petRepo.findById(petId)
                .ifPresentOrElse(
                        { petRepo.delete(it) },
                        { throw ResourceNotFoundException("Pet (id=${petId}) not found.") })
    }
}