package com.hamersztein.chorserver.jobs

import com.hamersztein.chorserver.infrastructure.RepositoryTest
import com.hamersztein.chorserver.jobs.model.entity.JobBidEntity
import com.hamersztein.chorserver.jobs.model.entity.JobRequestEntity
import com.hamersztein.chorserver.users.UserRepository
import com.hamersztein.chorserver.users.model.UserEntity
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.springframework.beans.factory.annotation.Autowired
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.time.Duration.Companion.hours

@RepositoryTest
class JobBidRepositoryTest {

    @Autowired
    private lateinit var jobBidRepository: JobBidRepository

    @Autowired
    private lateinit var jobRequestRepository: JobRequestRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var geometryFactory: GeometryFactory

    @Test
    fun `should save and find by id`() {
        runBlocking {
            val cleanerId = givenCleanerExists()
            val jobRequestId = givenJobRequestExists()

            val expectedJobBid = jobBidRepository.save(
                JobBidEntity(
                    jobRequestId = jobRequestId,
                    cleanerId = cleanerId,
                    price = 20.toBigDecimal(),
                )
            )

            assertNotNull(expectedJobBid.id!!)

            val fetchedJobBid = jobBidRepository.findById(expectedJobBid.id!!)

            assertNotNull(fetchedJobBid)
            assertEquals(expectedJobBid.copy(createdTime = fetchedJobBid.createdTime), fetchedJobBid)
        }
    }

    private suspend fun givenCleanerExists(): UUID {
        val cleaner = userRepository.save(
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

        return cleaner.id!!
    }

    private suspend fun givenJobRequestExists(): UUID {
        val customerId = givenCustomerExists()
        val point = geometryFactory.createPoint(Coordinate(51.42187302529486, -0.9448590942424676))

        val jobRequest = jobRequestRepository.save(
            JobRequestEntity(
                location = point,
                startTime = OffsetDateTime.now().truncatedTo(ChronoUnit.MICROS),
                duration = 4.hours,
                jobDetails = "Need a clean",
                customerId = customerId,
            )
        )

        return jobRequest.id!!
    }

    private suspend fun givenCustomerExists(): UUID {
        val customer = userRepository.save(
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

        return customer.id!!
    }
}
