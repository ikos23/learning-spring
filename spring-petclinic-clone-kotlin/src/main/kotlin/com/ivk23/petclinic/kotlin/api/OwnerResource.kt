package com.ivk23.petclinic.kotlin.api

import com.ivk23.petclinic.kotlin.model.Owner
import com.ivk23.petclinic.kotlin.model.Pet
import com.ivk23.petclinic.kotlin.service.PersonService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/owners")
class OwnerResource(private val personService: PersonService) {

    companion object {
        val log: Logger = LoggerFactory
                .getLogger(OwnerResource::class.java)
    }

    @GetMapping
    fun getAll() = personService.getAllOwners()

    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: Long) = personService.getOwnerById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Validated @RequestBody owner: Owner): Owner {
        log.debug("To be created: {}", owner)
        return personService.create(owner)
    }

    @PutMapping
    fun update(@Validated @RequestBody owner: Owner) = personService.update(owner)

    @PatchMapping("/{id}")
    fun patch(@PathVariable("id") ownerId: Long,
              @RequestBody owner: Owner): Owner =
            personService.patch(ownerId, owner)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") ownerId: Long) =
            personService.delete(ownerId)

    @GetMapping("/{id}/pets")
    fun getOwnerPets(@PathVariable("id") id: Long): Set<Pet> {
        return personService.getOwnerById(id).pets
    }
}