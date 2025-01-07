package com.hamersztein.chorserver.jobs

import com.hamersztein.chorserver.jobs.model.domain.Job
import com.hamersztein.chorserver.jobs.model.domain.toEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import java.time.OffsetDateTime
import java.util.UUID
import kotlin.test.assertNotNull
import kotlin.time.Duration.Companion.hours

class JobServiceTest {

    private val jobRepository = mockk<JobRepository>()

    private val jobService = JobService(jobRepository)

    @Test
    fun `should create job`() = runBlocking {
        val jobId = UUID.randomUUID()
        coEvery { jobRepository.save(job.toEntity()) } returns job.toEntity().copy(id = jobId)

        val newJob = jobService.createJob(job)

        assertNotNull(newJob)
        assertNotNull(newJob.id)

        assertEquals(job.copy(id = jobId), newJob)

        coVerify { jobRepository.save(job.toEntity()) }
    }

    companion object {
        private val geometryFactory = GeometryFactory()
        private val point = geometryFactory.createPoint(Coordinate(51.42187302529486, -0.9448590942424676))

        private val job = Job(
            location = point,
            startTime = OffsetDateTime.now(),
            duration = 4.hours,
            jobDetails = "Cleaning",
            customerId = UUID.randomUUID(),
            cleanerId = UUID.randomUUID(),
        )
    }

}
