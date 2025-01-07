package com.hamersztein.chorserver.jobs

import com.hamersztein.chorserver.jobs.model.domain.Job
import com.hamersztein.chorserver.jobs.model.domain.toEntity
import com.hamersztein.chorserver.jobs.model.entity.toDomain
import com.hamersztein.chorserver.jobs.model.exception.JobNotFoundException
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class JobService(private val jobRepository: JobRepository) {

    suspend fun createJob(job: Job) = jobRepository.save(job.toEntity()).toDomain()

    suspend fun getJob(jobId: UUID) =
        jobRepository.findById(jobId)?.toDomain() ?: throw JobNotFoundException(jobId)

}
