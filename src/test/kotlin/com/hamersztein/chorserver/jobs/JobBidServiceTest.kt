package com.hamersztein.chorserver.jobs

import com.hamersztein.chorserver.jobs.model.domain.JobBid
import com.hamersztein.chorserver.jobs.model.domain.toEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.UUID
import kotlin.test.assertNotNull

class JobBidServiceTest {

    private val jobBidRepository = mockk<JobBidRepository>()

    private val jobBidService = JobBidService(jobBidRepository)

    @Test
    fun `should create job bid`() = runBlocking {
        val jobBidId = UUID.randomUUID()
        coEvery { jobBidRepository.save(jobBid.toEntity()) } returns jobBid.toEntity().copy(id = jobBidId)

        val newJobBid = jobBidService.createJob(jobBid)

        assertNotNull(newJobBid)
        assertNotNull(newJobBid.id)

        assertEquals(jobBid.copy(id = jobBidId), newJobBid)

        coVerify { jobBidRepository.save(jobBid.toEntity()) }
    }

    companion object {
        private val jobBid = JobBid(
            jobRequestId = UUID.randomUUID(),
            cleanerId = UUID.randomUUID(),
            price = BigDecimal.TEN,
            createdTime = OffsetDateTime.now(),
        )
    }

}
