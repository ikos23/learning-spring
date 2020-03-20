package com.ivk23.petclinic.kotlin.controller

import com.ivk23.petclinic.kotlin.model.Owner
import com.ivk23.petclinic.kotlin.service.PersonService
import com.ivk23.petclinic.kotlin.service.PetService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/owners")
class OwnerController(private val personService: PersonService,
                      private val petService: PetService) {

    @GetMapping
    fun owners(model: Model): String {
        model.addAttribute("owners", personService.getAllOwners())
        return "person/owners"
    }

    @GetMapping("/{id}/profile")
    fun ownerProfile(@PathVariable("id") id: Long): ModelAndView {

        val modelAndView = ModelAndView("person/owner")

        modelAndView.addObject(
                "owner",
                personService.getOwnerById(id))

        return modelAndView
    }

    @GetMapping("/create")
    fun createOwner(model: Model) = "person/createOwner"

    @PostMapping("/create")
    fun createOwner(owner: Owner): String {
        val created = personService.create(owner)
        return "redirect:/owners/${created.id}/profile"
    }

    @GetMapping("/{ownerId}/pets/{petId}")
    fun pet(@PathVariable("ownerId") ownerId: Long,
            @PathVariable("petId") petId: Long): ModelAndView {

        val pet = petService.getPet(ownerId, petId)
        val owner = personService.getOwnerById(ownerId)

        val modelAndView = ModelAndView("pet/pet")
        modelAndView.addObject("pet", pet)
        modelAndView.addObject("owner", owner)

        return modelAndView
    }

    // this is not the right way to do things !
    @GetMapping("/{ownerId}/pets/{petId}/delete")
    fun deletePet(@PathVariable("ownerId") ownerId: Long,
                  @PathVariable("petId") petId: Long): String {

        petService.deletePet(ownerId, petId)
        return "redirect:/owners/$ownerId/profile"
    }

}