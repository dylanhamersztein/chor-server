package com.hamersztein.chorserver.users

import com.hamersztein.chorserver.users.model.CannotUpdateInactiveUserException
import com.hamersztein.chorserver.users.model.UpdateUserRequest
import com.hamersztein.chorserver.users.model.User
import com.hamersztein.chorserver.users.model.UserNotFoundException
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import java.util.*

@WebFluxTest(UserController::class)
class UserControllerTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockkBean
    private lateinit var userService: UserService

    @Test
    fun `should fetch existing user and return 200`() = runBlocking {
        coEvery { userService.getUser(user.id!!) } returns user

        webTestClient.get()
            .uri("/users/${user.id}")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(user.id!!)
            .jsonPath("$.firstName").isEqualTo(user.firstName)
            .jsonPath("$.middleNames").doesNotExist()
            .jsonPath("$.lastName").isEqualTo(user.lastName)
            .jsonPath("$.addressLine1").isEqualTo(user.addressLine1)
            .jsonPath("$.addressLine2").isEqualTo(user.addressLine2!!)
            .jsonPath("$.town").isEqualTo(user.town!!)
            .jsonPath("$.city").isEqualTo(user.city!!)
            .jsonPath("$.postCode").isEqualTo(user.postCode)
            .jsonPath("$.active").isEqualTo(true)

        coVerify(exactly = 1) { userService.getUser(user.id!!) }
    }

    @Test
    fun `should return 404 when user not found`() = runBlocking {
        coEvery { userService.getUser(any()) } throws UserNotFoundException(user.id!!)

        webTestClient.get()
            .uri("/users/${user.id}")
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .jsonPath("$.title").isEqualTo("User not found")
            .jsonPath("$.status").isEqualTo(404)
            .jsonPath("$.detail").isEqualTo("User with id [${user.id!!}] not found")

        coVerify(exactly = 1) { userService.getUser(user.id!!) }
    }

    @Test
    fun `should create user and return 201`() = runBlocking {
        val userRequest = user.copy(id = null)

        coEvery { userService.createUser(userRequest) } returns user

        webTestClient.post()
            .uri("/users")
            .contentType(APPLICATION_JSON)
            .body(BodyInserters.fromValue(userRequest))
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("$.id").isEqualTo(user.id!!)
            .jsonPath("$.firstName").isEqualTo(user.firstName)
            .jsonPath("$.middleNames").doesNotExist()
            .jsonPath("$.lastName").isEqualTo(user.lastName)
            .jsonPath("$.addressLine1").isEqualTo(user.addressLine1)
            .jsonPath("$.addressLine2").isEqualTo(user.addressLine2!!)
            .jsonPath("$.town").isEqualTo(user.town!!)
            .jsonPath("$.city").isEqualTo(user.city!!)
            .jsonPath("$.postCode").isEqualTo(user.postCode)
            .jsonPath("$.active").isEqualTo(true)

        coVerify(exactly = 1) { userService.createUser(userRequest) }
    }

    @Test
    fun `should return 400 when creating user and firstName is empty`() {
        runBlocking {
            val userRequest = user.copy(firstName = "")

            webTestClient.post()
                .uri("/users")
                .contentType(APPLICATION_JSON)
                .body(BodyInserters.fromValue(userRequest))
                .exchange()
                .expectStatus().isBadRequest
                .expectBody()
                .jsonPath("$.status").isEqualTo(400)
                .jsonPath("$.detail").isEqualTo("Validation failed")
                .jsonPath("$.errors").exists()
                .jsonPath("$.errors").isMap()
                .jsonPath("$.errors.size()").isEqualTo(1)
                .jsonPath("$.errors['firstName']").exists()
                .jsonPath("$.errors['firstName']").isEqualTo("must not be empty")

            coVerify(exactly = 0) { userService.createUser(any()) }
        }
    }

    @Test
    fun `should return 400 when creating user and lastName is empty`() {
        runBlocking {
            val userRequest = user.copy(lastName = "")

            webTestClient.post()
                .uri("/users")
                .contentType(APPLICATION_JSON)
                .body(BodyInserters.fromValue(userRequest))
                .exchange()
                .expectStatus().isBadRequest
                .expectBody()
                .jsonPath("$.status").isEqualTo(400)
                .jsonPath("$.detail").isEqualTo("Validation failed")
                .jsonPath("$.errors").exists()
                .jsonPath("$.errors").isMap()
                .jsonPath("$.errors.size()").isEqualTo(1)
                .jsonPath("$.errors['lastName']").exists()
                .jsonPath("$.errors['lastName']").isEqualTo("must not be empty")

            coVerify(exactly = 0) { userService.createUser(any()) }
        }
    }

    @Test
    fun `should return 400 when creating user and addressLine1 is empty`() {
        runBlocking {
            val userRequest = user.copy(addressLine1 = "")

            webTestClient.post()
                .uri("/users")
                .contentType(APPLICATION_JSON)
                .body(BodyInserters.fromValue(userRequest))
                .exchange()
                .expectStatus().isBadRequest
                .expectBody()
                .jsonPath("$.status").isEqualTo(400)
                .jsonPath("$.detail").isEqualTo("Validation failed")
                .jsonPath("$.errors").exists()
                .jsonPath("$.errors").isMap()
                .jsonPath("$.errors.size()").isEqualTo(1)
                .jsonPath("$.errors['addressLine1']").exists()
                .jsonPath("$.errors['addressLine1']").isEqualTo("must not be empty")

            coVerify(exactly = 0) { userService.createUser(any()) }
        }
    }

    @Test
    fun `should return 400 when creating user and postCode is empty`() {
        runBlocking {
            val userRequest = user.copy(postCode = "")

            webTestClient.post()
                .uri("/users")
                .contentType(APPLICATION_JSON)
                .body(BodyInserters.fromValue(userRequest))
                .exchange()
                .expectStatus().isBadRequest
                .expectBody()
                .jsonPath("$.status").isEqualTo(400)
                .jsonPath("$.detail").isEqualTo("Validation failed")
                .jsonPath("$.errors").exists()
                .jsonPath("$.errors").isMap()
                .jsonPath("$.errors.size()").isEqualTo(1)
                .jsonPath("$.errors['postCode']").exists()
                .jsonPath("$.errors['postCode']").isEqualTo("must not be empty")

            coVerify(exactly = 0) { userService.createUser(any()) }
        }
    }

    @Test
    fun `should return 200 and updated user`() {
        runBlocking {
            val updateUserRequest = UpdateUserRequest(firstName = "Something else")

            coEvery {
                userService.updateUser(user.id!!, updateUserRequest)
            } returns user.copy(firstName = updateUserRequest.firstName!!)

            webTestClient.patch()
                .uri("/users/${user.id}")
                .body(BodyInserters.fromValue(updateUserRequest))
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.id").isEqualTo(user.id!!)
                .jsonPath("$.firstName").isEqualTo(updateUserRequest.firstName!!)
                .jsonPath("$.middleNames").doesNotExist()
                .jsonPath("$.lastName").isEqualTo(user.lastName)
                .jsonPath("$.addressLine1").isEqualTo(user.addressLine1)
                .jsonPath("$.addressLine2").isEqualTo(user.addressLine2!!)
                .jsonPath("$.town").isEqualTo(user.town!!)
                .jsonPath("$.city").isEqualTo(user.city!!)
                .jsonPath("$.postCode").isEqualTo(user.postCode)
                .jsonPath("$.active").isEqualTo(true)

            coVerify(exactly = 1) { userService.updateUser(user.id!!, updateUserRequest) }
        }
    }

    @Test
    fun `should return 404 when attempting to update user that does not exist`() {
        runBlocking {
            coEvery { userService.updateUser(user.id!!, any()) } throws UserNotFoundException(user.id!!)

            webTestClient.patch()
                .uri("/users/${user.id}")
                .body(BodyInserters.fromValue(UpdateUserRequest()))
                .exchange()
                .expectStatus().isNotFound
                .expectBody()
                .jsonPath("$.title").isEqualTo("User not found")
                .jsonPath("$.status").isEqualTo(404)
                .jsonPath("$.detail").isEqualTo("User with id [${user.id!!}] not found")

            coVerify(exactly = 1) { userService.updateUser(user.id!!, any()) }
        }
    }

    @Test
    fun `should return 409 when attempting to update inactive user`() {
        runBlocking {
            coEvery { userService.updateUser(user.id!!, any()) } throws CannotUpdateInactiveUserException(user.id!!)

            webTestClient.patch()
                .uri("/users/${user.id}")
                .body(BodyInserters.fromValue(UpdateUserRequest()))
                .exchange()
                .expectStatus().isEqualTo(CONFLICT)
                .expectBody()
                .jsonPath("$.title").isEqualTo("Cannot update inactive user")
                .jsonPath("$.status").isEqualTo(409)
                .jsonPath("$.detail").isEqualTo("User with id [${user.id}] is inactive and cannot be updated")

            coVerify(exactly = 1) { userService.updateUser(user.id!!, any()) }
        }
    }

    @Test
    fun `should return 200 when marking user as inactive and return active as false`() {
        runBlocking {
            coEvery { userService.updateUserActive(user.id!!, false) } returns user.copy(active = false)

            webTestClient.delete()
                .uri("/users/${user.id}")
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.id").isEqualTo(user.id!!)
                .jsonPath("$.firstName").isEqualTo(user.firstName)
                .jsonPath("$.middleNames").doesNotExist()
                .jsonPath("$.lastName").isEqualTo(user.lastName)
                .jsonPath("$.addressLine1").isEqualTo(user.addressLine1)
                .jsonPath("$.addressLine2").isEqualTo(user.addressLine2!!)
                .jsonPath("$.town").isEqualTo(user.town!!)
                .jsonPath("$.city").isEqualTo(user.city!!)
                .jsonPath("$.postCode").isEqualTo(user.postCode)
                .jsonPath("$.active").isEqualTo(false)

            coVerify(exactly = 1) { userService.updateUserActive(user.id!!, false) }
        }
    }

    @Test
    fun `should return 404 when marking user that does not exist as inactive`() {
        runBlocking {
            coEvery { userService.updateUserActive(user.id!!, false) } throws UserNotFoundException(user.id!!)

            webTestClient.delete()
                .uri("/users/${user.id}")
                .exchange()
                .expectStatus().isNotFound
                .expectBody()
                .jsonPath("$.title").isEqualTo("User not found")
                .jsonPath("$.status").isEqualTo(404)
                .jsonPath("$.detail").isEqualTo("User with id [${user.id!!}] not found")

            coVerify(exactly = 1) { userService.updateUserActive(user.id!!, false) }
        }
    }

    @Test
    fun `should return 200 when marking user as active and return active as true`() {
        runBlocking {
            coEvery { userService.updateUserActive(user.id!!, true) } returns user.copy(active = true)

            webTestClient.post()
                .uri("/users/${user.id}/activate")
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.id").isEqualTo(user.id!!)
                .jsonPath("$.firstName").isEqualTo(user.firstName)
                .jsonPath("$.middleNames").doesNotExist()
                .jsonPath("$.lastName").isEqualTo(user.lastName)
                .jsonPath("$.addressLine1").isEqualTo(user.addressLine1)
                .jsonPath("$.addressLine2").isEqualTo(user.addressLine2!!)
                .jsonPath("$.town").isEqualTo(user.town!!)
                .jsonPath("$.city").isEqualTo(user.city!!)
                .jsonPath("$.postCode").isEqualTo(user.postCode)
                .jsonPath("$.active").isEqualTo(true)

            coVerify(exactly = 1) { userService.updateUserActive(user.id!!, true) }
        }
    }

    @Test
    fun `should return 404 when marking user that does not exist as active`() {
        runBlocking {
            coEvery { userService.updateUserActive(user.id!!, true) } throws UserNotFoundException(user.id!!)

            webTestClient.post()
                .uri("/users/${user.id}/activate")
                .exchange()
                .expectStatus().isNotFound
                .expectBody()
                .jsonPath("$.title").isEqualTo("User not found")
                .jsonPath("$.status").isEqualTo(404)
                .jsonPath("$.detail").isEqualTo("User with id [${user.id!!}] not found")

            coVerify(exactly = 1) { userService.updateUserActive(user.id!!, true) }
        }
    }

    companion object {
        private val user = User(
            id = UUID.randomUUID(),
            firstName = "Dylan",
            lastName = "Turmeric",
            addressLine1 = "10 Sausage Way",
            addressLine2 = "15  Sentinel Drive",
            town = "Bromley",
            city = "London",
            postCode = "NW9 5SA",
            active = true
        )
    }
}
