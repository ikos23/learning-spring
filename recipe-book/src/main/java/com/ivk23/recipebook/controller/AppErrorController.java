package com.ivk23.recipebook.controller;

import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class AppErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;

    public AppErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @GetMapping("/error")
    public ModelAndView error(HttpServletRequest request) {
        final var errorAttributes = this.errorAttributes.getErrorAttributes(new ServletWebRequest(request), true);
        return new ModelAndView("error", errorAttributes);
    }


    @Override
    public String getErrorPath() {
        return "/error";
    }
}
