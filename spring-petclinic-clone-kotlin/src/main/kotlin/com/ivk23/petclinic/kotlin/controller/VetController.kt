package com.ivk23.petclinic.kotlin.controller

import com.ivk23.petclinic.kotlin.service.PersonService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/vets")
class VetController(private val personService: PersonService) {

    @GetMapping
    fun vets(model: Model): String {
        model.addAttribute("vets", personService.getAllVets())
        return "person/vets"
    }

}