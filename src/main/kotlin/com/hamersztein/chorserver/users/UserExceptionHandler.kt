package com.hamersztein.chorserver.users

import com.hamersztein.chorserver.users.model.CannotUpdateInactiveUserException
import com.hamersztein.chorserver.users.model.UserNotFoundException
import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class UserExceptionHandler {

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFound(ex: UserNotFoundException): ResponseEntity<ProblemDetail> {
        val problemDetail = ProblemDetail.forStatusAndDetail(NOT_FOUND, ex.message).apply {
            title = "User not found"
        }

        return ResponseEntity.status(NOT_FOUND).body(problemDetail)
    }

    @ExceptionHandler(CannotUpdateInactiveUserException::class)
    fun handleCannotUpdateInactiveUserException(ex: CannotUpdateInactiveUserException): ResponseEntity<ProblemDetail> {
        val problemDetail = ProblemDetail.forStatusAndDetail(CONFLICT, ex.message).apply {
            title = "Cannot update inactive user"
        }

        return ResponseEntity.status(CONFLICT).body(problemDetail)
    }
}
