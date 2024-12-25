package com.hamersztein.chorserver.users

import com.hamersztein.chorserver.infrastructure.RepositoryTest
import com.hamersztein.chorserver.users.model.UserEntity
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertNotNull

@RepositoryTest
class UserRepositoryTest {

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun `should save and find by ID`() {
        runBlocking {
            val expectedUser = userRepository.save(
                UserEntity(
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
