package com.hamersztein.chorserver.users

import com.hamersztein.chorserver.users.model.UpdateUserRequest
import com.hamersztein.chorserver.users.model.User
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.MediaType.APPLICATION_PROBLEM_JSON_VALUE
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.*

@Tag(name = "User Controller")
@ApiResponses(
    value = [
        ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = [Content(
                schema = Schema(
                    implementation = ProblemDetail::class,
                    contentMediaType = APPLICATION_PROBLEM_JSON_VALUE
                )
            )]
        ),
        ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = [Content(
                schema = Schema(
                    implementation = ProblemDetail::class,
                    contentMediaType = APPLICATION_PROBLEM_JSON_VALUE
                )
            )]
        )
    ]
)
@RestController
@RequestMapping(value = ["/users"], produces = [APPLICATION_JSON_VALUE])
class UserController(private val userService: UserService) {

    @Operation(summary = "Create a new user")
    @ResponseStatus(CREATED)
    @PostMapping(consumes = [APPLICATION_JSON_VALUE])
    suspend fun createUser(@RequestBody @Valid user: User) = userService.createUser(user)

    @Operation(summary = "Get a user")
    @ResponseStatus(OK)
    @GetMapping("/{userId}")
    suspend fun getUser(@PathVariable userId: UUID) = userService.getUser(userId)

    @Operation(summary = "Update a user")
    @ApiResponse(
        responseCode = "409",
        description = "Cannot update inactive user",
        content = [Content(
            schema = Schema(
                implementation = ProblemDetail::class,
                contentMediaType = APPLICATION_PROBLEM_JSON_VALUE
            )
        )]
    )
    @ResponseStatus(OK)
    @PatchMapping("/{userId}")
    suspend fun updateUser(@PathVariable userId: UUID, @RequestBody @Valid updateUserRequest: UpdateUserRequest) =
        userService.updateUser(userId, updateUserRequest)

    @Operation(summary = "Mark user as inactive")
    @ResponseStatus(OK)
    @DeleteMapping("/{userId}")
    suspend fun markUserInactive(@PathVariable userId: UUID) = userService.updateUserActive(userId, false)

    @Operation(summary = "Mark user as active")
    @ResponseStatus(OK)
    @PostMapping("/{userId}/activate")
    suspend fun markUserActive(@PathVariable userId: UUID) = userService.updateUserActive(userId, true)
}
