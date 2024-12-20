package com.hamersztein.chorserver.users

import com.hamersztein.chorserver.users.model.*
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.*
import kotlin.test.assertEquals

class UserServiceTest {

    private val userRepository: UserRepository = mockk()

    private val userService = UserService(userRepository)

    @Test
    fun `should save and return new users`() = runBlocking {
        coEvery { userRepository.save(user.toEntity()) } returns user.toEntity()

        val actualUser = userService.createUser(user)

        coVerify(exactly = 1) { userRepository.save(user.toEntity()) }
        assertEquals(user, actualUser)
    }

    @Test
    fun `should find user by id when user exists`() = runBlocking {
        coEvery { userRepository.findById(user.id!!) } returns user.toEntity()

        val actualUser = userService.getUser(user.id!!)

        coVerify(exactly = 1) { userRepository.findById(user.id!!) }
        assertEquals(user, actualUser)
    }

    @Test
    fun `should throw user not found exception when user is not found`() = runBlocking {
        val userId = UUID.randomUUID()

        coEvery { userRepository.findById(any()) } returns null

        val exception = assertThrows<UserNotFoundException> {
            userService.getUser(userId)
        }

        coVerify(exactly = 1) { userRepository.findById(userId) }
        assertEquals(exception.message, "User with id [$userId] not found")
    }

    @ValueSource(booleans = [true, false])
    @ParameterizedTest(name = "should update user with active = {0}")
    fun `should update user active status`(active: Boolean) = runBlocking {
        coEvery { userRepository.existsById(user.id!!) } returns true

        val inactiveUser = user.copy(active = active)

        coEvery { userRepository.updateUserActive(inactiveUser.id!!, active) } returns inactiveUser.toEntity()

        val updatedUser = userService.updateUserActive(user.id!!, active)

        coVerify(exactly = 1) {
            userRepository.updateUserActive(user.id!!, active)
        }
        assertEquals(inactiveUser, updatedUser)
    }

    @Test
    fun `should throw user not found exception when updating user active that doesn't exist`() = runBlocking {
        val userId = UUID.randomUUID()

        coEvery { userRepository.existsById(any()) } returns false

        val exception = assertThrows<UserNotFoundException> {
            userService.updateUserActive(userId, false)
        }

        coVerify(exactly = 1) { userRepository.existsById(userId) }
        assertEquals(exception.message, "User with id [$userId] not found")
    }

    @Test
    fun `should update user`() = runBlocking {
        coEvery { userRepository.findById(any()) } returns user.toEntity()

        val expectedUpdate = user.copy(firstName = "New first name")

        coEvery { userRepository.save(expectedUpdate.toEntity()) } returns expectedUpdate.toEntity()

        val updateUserRequest = UpdateUserRequest(firstName = "New first name")

        val updatedUser = userService.updateUser(user.id!!, updateUserRequest)

        coVerifyOrder {
            userRepository.findById(user.id!!)
            userRepository.save(updatedUser.toEntity())
        }
        assertEquals(expectedUpdate, updatedUser)
    }

    @Test
    fun `should throw user not found exception when updating user that doesn't exist`() = runBlocking {
        val userId = UUID.randomUUID()

        coEvery { userRepository.findById(any()) } returns null

        val updateUserRequest = UpdateUserRequest(firstName = "New first name")

        val exception = assertThrows<UserNotFoundException> {
            userService.updateUser(userId, updateUserRequest)
        }

        coVerify(exactly = 1) { userRepository.findById(userId) }
        coVerify(exactly = 0) { userRepository.save(any()) }
        assertEquals(exception.message, "User with id [$userId] not found")
    }

    @Test
    fun `should throw cannot update inactive user exception when user is inactive`() = runBlocking {
        val inactiveUser = user.copy(active = false)

        coEvery { userRepository.findById(any()) } returns inactiveUser.toEntity()

        val updateUserRequest = UpdateUserRequest(firstName = "New first name")

        val exception = assertThrows<CannotUpdateInactiveUserException> {
            userService.updateUser(inactiveUser.id!!, updateUserRequest)
        }

        coVerify(exactly = 1) { userRepository.findById(inactiveUser.id!!) }
        coVerify(exactly = 0) { userRepository.save(any()) }
        assertEquals(exception.message, "User with id [${inactiveUser.id}] is inactive and cannot be updated")
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
