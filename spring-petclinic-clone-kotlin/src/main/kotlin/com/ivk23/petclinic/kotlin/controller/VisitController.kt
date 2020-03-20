package com.ivk23.petclinic.kotlin.controller

import com.ivk23.petclinic.kotlin.model.Visit
import com.ivk23.petclinic.kotlin.service.PersonService
import com.ivk23.petclinic.kotlin.service.VisitService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/visits")
class VisitController(private val visitService: VisitService,
                      private val personService: PersonService) {

    companion object {
        val log: Logger = LoggerFactory
                .getLogger(VisitController::class.java)
    }

    @GetMapping
    fun index(model: Model): String {
        model.addAttribute("visits", visitService.getAll())
        return "visit/index"
    }

    @GetMapping("/create")
    fun create(model: Model): String {
        model["owners"] = personService.getAllOwners()
        return "visit/create"
    }

    @PostMapping
    fun create(@Validated visit: Visit, bindingResult: BindingResult): String {
        if (bindingResult.hasErrors()) {
            log.error("Create Visit Failed.! Validation Errors.")
            bindingResult.fieldErrors.forEach {
                log.error("${it.field}, ${it.rejectedValue}, ${it.defaultMessage}")
            }
            return "redirect:/visits/create"
        }
        visitService.create(visit)
        return "redirect:/visits"
    }

}