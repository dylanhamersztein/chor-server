package com.hamersztein.chorserver.jobs.model.domain

import com.hamersztein.chorserver.jobs.model.entity.JobEntity
import org.locationtech.jts.geom.Point
import java.time.OffsetDateTime
import java.util.UUID
import kotlin.time.Duration

data class Job(
    val id: UUID? = null,
    val location: Point,
    val startTime: OffsetDateTime,
    val duration: Duration,
    val jobDetails: String?,
    val customerId: UUID,
    val cleanerId: UUID
)

fun Job.toEntity() = JobEntity(
    id = id,
    location = location,
    startTime = startTime,
    duration = duration,
    jobDetails = jobDetails,
    customerId = customerId,
    cleanerId = cleanerId
)
