package com.hamersztein.chorserver.jobs

import com.hamersztein.chorserver.jobs.model.domain.JobBid
import com.hamersztein.chorserver.jobs.model.domain.toEntity
import com.hamersztein.chorserver.jobs.model.entity.toDomain
import com.hamersztein.chorserver.jobs.model.exception.JobBidNotFoundException
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class JobBidService(private val jobBidRepository: JobBidRepository) {

    suspend fun createJob(jobBid: JobBid) = jobBidRepository.save(jobBid.toEntity()).toDomain()

    suspend fun getJob(jobBidId: UUID) =
        jobBidRepository.findById(jobBidId)?.toDomain() ?: throw JobBidNotFoundException(jobBidId)

}
