package com.hamersztein.chorserver.shared

import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.support.WebExchangeBindException

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(WebExchangeBindException::class)
    fun handleValidationExceptions(ex: WebExchangeBindException): ResponseEntity<ProblemDetail> {
        val errors = ex.bindingResult.fieldErrors.associateBy({ it.field }, { it.defaultMessage })

        val problemDetail = ProblemDetail.forStatusAndDetail(BAD_REQUEST, "Validation failed")
        problemDetail.properties = mapOf("errors" to errors)

        return ResponseEntity.badRequest().body(problemDetail)
    }
}
