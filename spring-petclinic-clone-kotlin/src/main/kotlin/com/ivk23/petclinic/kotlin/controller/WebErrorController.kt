package com.ivk23.petclinic.kotlin.controller

import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest

@Controller
class WebErrorController(private val errorAttributes: ErrorAttributes) : ErrorController {

    override fun getErrorPath() = "/error"

    @GetMapping("/error")
    fun error(req: HttpServletRequest): ModelAndView {
        val errorDetails = this.errorAttributes.getErrorAttributes(ServletWebRequest(req), true)
        return ModelAndView("error", errorDetails)
    }
}