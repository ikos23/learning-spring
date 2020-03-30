package com.ivk23.petclinic.kotlin.api.v1

import com.ivk23.petclinic.kotlin.api.Constants.Companion.API_V1_PREFIX
import com.ivk23.petclinic.kotlin.model.Owner
import com.ivk23.petclinic.kotlin.model.Pet
import com.ivk23.petclinic.kotlin.service.PersonService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@Api(description = "This is Owner Resource API")
@RestController
@RequestMapping("/$API_V1_PREFIX/owners")
class OwnerResource(private val personService: PersonService) {

    companion object {
        val log: Logger = LoggerFactory
                .getLogger(OwnerResource::class.java)
    }

    @ApiOperation(value = "Get All Owners.", response = List::class)
    @ApiResponses(ApiResponse(code = 200, message = "List of owners returned. Or empty list if there are no data."))
    @GetMapping
    fun getAll() = personService.getAllOwners()

    @ApiOperation(value = "Get Owners by ID.", response = Owner::class)
    @ApiResponses(ApiResponse(code = 404, message = "Owner is not found."))
    @GetMapping("/{id}")
    fun getById(@PathVariable("id") id: Long) = personService.getOwnerById(id)

    @ApiOperation(value = "Create new Owner.", response = Owner::class)
    @ApiResponses(ApiResponse(code = 201, message = "Owner created successfully."))
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