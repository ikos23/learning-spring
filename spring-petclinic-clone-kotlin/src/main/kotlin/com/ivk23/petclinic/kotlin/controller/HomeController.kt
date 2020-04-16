package com.ivk23.petclinic.kotlin.controller

import com.ivk23.petclinic.kotlin.service.PersonService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController(private val personService: PersonService) {

    @GetMapping(value = ["", "/", "/index", "/index.html"])
    fun index(model: Model): String {
        model["personsTotal"] = personService.getAllPersonsCount()
        return "index"
    }

    @GetMapping("/admin")
    fun admin(@AuthenticationPrincipal activeUser: User,
              model: Model): String {
        model["currentUser"] = activeUser.username
        model["roles"] = activeUser.authorities.joinToString(",")
        return "adminConsole"
    }

}