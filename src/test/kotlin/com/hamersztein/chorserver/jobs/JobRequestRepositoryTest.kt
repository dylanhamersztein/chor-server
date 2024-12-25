package com.hamersztein.chorserver.jobs

import com.hamersztein.chorserver.infrastructure.RepositoryTest
import com.hamersztein.chorserver.jobs.model.JobRequestEntity
import com.hamersztein.chorserver.users.UserRepository
import com.hamersztein.chorserver.users.model.UserEntity
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.springframework.beans.factory.annotation.Autowired
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit.MICROS
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.time.Duration.Companion.hours

@RepositoryTest
class JobRequestRepositoryTest {

    @Autowired
    private lateinit var jobRequestRepository: JobRequestRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var geometryFactory: GeometryFactory

    @Test
    fun `should save and find by id`() {
        runBlocking {
            val customerId = givenCustomerExists()

            val point = geometryFactory.createPoint(Coordinate(51.42187302529486, -0.9448590942424676))

            val expectedJobRequest = jobRequestRepository.save(
                JobRequestEntity(
                    location = point,
                    startTime = OffsetDateTime.now().truncatedTo(MICROS),
                    duration = 4.hours,
                    jobDetails = "Something",
                    customerId = customerId,
                )
            )

            assertNotNull(expectedJobRequest.id!!)

            val fetchedJobRequest = jobRequestRepository.findById(expectedJobRequest.id!!)

            assertNotNull(fetchedJobRequest)
            assertEquals(expectedJobRequest, fetchedJobRequest)
        }
    }

    private suspend fun givenCustomerExists(): UUID {
        val user = userRepository.save(
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

        return user.id!!
    }
}
