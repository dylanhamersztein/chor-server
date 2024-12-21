package com.hamersztein.chorserver.users

import com.hamersztein.chorserver.users.model.UpdateUserRequest
import com.hamersztein.chorserver.users.model.User
import jakarta.validation.Valid
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
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

@RestController
@RequestMapping(value = ["/users"], produces = [APPLICATION_JSON_VALUE])
class UserController(private val userService: UserService) {

    @ResponseStatus(CREATED)
    @PostMapping(consumes = [APPLICATION_JSON_VALUE])
    suspend fun createUser(@RequestBody @Valid user: User) = userService.createUser(user)

    @ResponseStatus(OK)
    @GetMapping("/{userId}")
    suspend fun getUser(@PathVariable userId: UUID) = userService.getUser(userId)

    @ResponseStatus(OK)
    @PatchMapping("/{userId}")
    suspend fun updateUser(@PathVariable userId: UUID, @RequestBody @Valid updateUserRequest: UpdateUserRequest) =
        userService.updateUser(userId, updateUserRequest)

    @ResponseStatus(OK)
    @DeleteMapping("/{userId}")
    suspend fun markUserInactive(@PathVariable userId: UUID) = userService.updateUserActive(userId, false)

    @ResponseStatus(OK)
    @PostMapping("/{userId}/activate")
    suspend fun markUserActive(@PathVariable userId: UUID) = userService.updateUserActive(userId, true)
}
