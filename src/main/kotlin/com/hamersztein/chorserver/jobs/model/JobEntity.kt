package com.hamersztein.chorserver.jobs.model

import org.locationtech.jts.geom.Point
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.OffsetDateTime
import java.util.UUID
import kotlin.time.Duration

@Table(name = "jobs")
data class JobEntity(
    @Id
    val id: UUID? = null,
    val location: Point,
    val startTime: OffsetDateTime,
    val duration: Duration,
    val jobDetails: String?,
    val customerId: UUID,
    val cleanerId: UUID
)
