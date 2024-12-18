package com.hamersztein.chorserver.users.repository

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import kotlin.test.assertNotNull

@DataR2dbcTest
class UserRepositoryTest {

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun `should save and find by ID`() {
        runBlocking {
            val expectedUser = userRepository.save(
                User(
                    firstName = "Dylan",
                    lastName = "Turmeric",
                    addressLine1 = "10 Sausage Way",
                    addressLine2 = "15  Sentinel Drive",
                    town = "Bromley",
                    city = "London",
                    postCode = "NW9 5SA"
                )
            )
            assertNotNull(expectedUser.id)

            val fetchedUser = userRepository.findById(expectedUser.id!!)

            assertNotNull(fetchedUser)
            assertEquals(expectedUser, fetchedUser)
        }
    }
}
