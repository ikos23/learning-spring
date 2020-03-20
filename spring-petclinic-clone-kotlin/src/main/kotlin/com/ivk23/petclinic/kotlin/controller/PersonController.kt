package com.ivk23.petclinic.kotlin.controller

import com.ivk23.petclinic.kotlin.model.Person
import com.ivk23.petclinic.kotlin.service.PersonService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView

@Controller
class PersonController(private val personService: PersonService) {

    @GetMapping("/persons")
    fun persons(model: Model): String {
        model["owners"] = personService.getAllOwners()
        model["vets"] = personService.getAllVets()
        return "person/index"
    }

    @PostMapping("/persons/search")
    fun search(person: Person): ModelAndView {
        val persons = personService.getAllBy(person.firstName, person.lastName)
        val modelAndView = ModelAndView("person/searchResult")
        modelAndView.addObject("persons", persons)

        return modelAndView
    }

    @GetMapping("/persons/{id}/profile")
    fun redirectToProfile(
        @PathVariable("id") id: Long,
        @RequestParam("type") type: String
    ): String {
        return when (type) {
            "vet" -> "redirect:/vets/$id/profile"
            "owner" -> "redirect:/owners/$id/profile"
            else -> throw IllegalArgumentException("Wrong person type $type. Check query param ?type=$type ")
        }
    }

    @GetMapping("/vets/{id}/profile")
    fun vetProfile(@PathVariable("id") id: Long): ModelAndView {
        val modelAndView = ModelAndView("person/vet")
        modelAndView.addObject("vet", personService.getVetById(id))
        return modelAndView
    }




}