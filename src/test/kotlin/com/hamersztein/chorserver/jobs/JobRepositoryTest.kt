package com.hamersztein.chorserver.jobs

import com.hamersztein.chorserver.infrastructure.RepositoryTest
import com.hamersztein.chorserver.jobs.model.JobEntity
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
class JobRepositoryTest {

    @Autowired
    private lateinit var jobRepository: JobRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var geometryFactory: GeometryFactory

    @Test
    fun `should save and find by id`() {
        runBlocking {
            val (customerId, cleanerId) = givenCustomerAndCleanerExist()

            val point = geometryFactory.createPoint(Coordinate(51.42187302529486, -0.9448590942424676))

            val expectedJob = jobRepository.save(
                JobEntity(
                    location = point,
                    startTime = OffsetDateTime.now().truncatedTo(MICROS),
                    duration = 4.hours,
                    jobDetails = "Something",
                    customerId = customerId,
                    cleanerId = cleanerId
                )
            )

            assertNotNull(expectedJob.id!!)

            val fetchedJob = jobRepository.findById(expectedJob.id!!)

            assertNotNull(fetchedJob)
            assertEquals(expectedJob, fetchedJob)
        }
    }

    private suspend fun givenCustomerAndCleanerExist(): Pair<UUID, UUID> {
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

        val cleaner = userRepository.save(
            UserEntity(
                firstName = "Dylan2",
                lastName = "Turmeric2",
                addressLine1 = "10 Sausage Way2",
                addressLine2 = "15  Sentinel Drive2",
                town = "Bromley",
                city = "London",
                postCode = "NW9 5SB"
            )
        )

        return user.id!! to cleaner.id!!
    }
}
