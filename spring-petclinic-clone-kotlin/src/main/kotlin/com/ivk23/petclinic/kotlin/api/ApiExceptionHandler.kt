package com.ivk23.petclinic.kotlin.api

import com.ivk23.petclinic.kotlin.exception.ResourceNotFoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.validation.ConstraintViolationException

/**
 * Exceptions Handler for API. It is used to provide appropriate
 * handling for any exception thrown by REST API endpoints.
 *
 * Docs:
 * https://docs.spring.io/spring/docs/5.2.3.RELEASE/spring-framework-reference/web.html#mvc-ann-exceptionhandler
 * https://docs.spring.io/spring/docs/5.2.3.RELEASE/spring-framework-reference/web.html#mvc-exceptionhandlers
 */
@ControllerAdvice(annotations = [RestController::class])
class ApiExceptionHandler : ResponseEntityExceptionHandler() {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(ex: ResourceNotFoundException): String {
        return ex.message
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(ex: ConstraintViolationException): ResponseEntity<Any> {
        val errors = ex.constraintViolations
                .map {
                    object {
                        val field = it.propertyPath
                        val message = it.message
                    }
                }
                .groupBy({ obj -> obj.field }) { it.message }
        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }

    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException,
                                              headers: HttpHeaders,
                                              status: HttpStatus,
                                              request: WebRequest): ResponseEntity<Any> {

        return handleExceptionInternal(
                ex, gerErrors(ex.bindingResult.fieldErrors),
                headers, status, request)
    }

    private val gerErrors = { fieldErrors: List<FieldError> ->
        fieldErrors
                .asSequence()
                .map {
                    object {
                        val field = it.field
                        val message = it.defaultMessage
                    }
                }
                .groupBy({ obj -> obj.field }) { it.message }
    }

}