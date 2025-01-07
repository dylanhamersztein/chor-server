package com.hamersztein.chorserver.jobs

import com.hamersztein.chorserver.jobs.model.entity.JobEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.UUID

interface JobRepository : CoroutineCrudRepository<JobEntity, UUID>
