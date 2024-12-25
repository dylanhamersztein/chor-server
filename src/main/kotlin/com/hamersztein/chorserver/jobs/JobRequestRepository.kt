package com.hamersztein.chorserver.jobs

import com.hamersztein.chorserver.jobs.model.JobRequestEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.UUID

interface JobRequestRepository : CoroutineCrudRepository<JobRequestEntity, UUID>
