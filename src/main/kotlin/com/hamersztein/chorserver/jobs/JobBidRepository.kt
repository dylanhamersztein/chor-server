package com.hamersztein.chorserver.jobs

import com.hamersztein.chorserver.jobs.model.JobBidEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.UUID

interface JobBidRepository : CoroutineCrudRepository<JobBidEntity, UUID>
