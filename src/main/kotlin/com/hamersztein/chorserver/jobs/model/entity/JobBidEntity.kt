package com.hamersztein.chorserver.jobs.model.entity

import com.hamersztein.chorserver.jobs.model.domain.JobBid
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.ReadOnlyProperty
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.UUID

@Table(name = "job_bids")
data class JobBidEntity(
    @Id
    val id: UUID? = null,
    val jobRequestId: UUID,
    val cleanerId: UUID,
    val price: BigDecimal,
    @ReadOnlyProperty
    val createdTime: OffsetDateTime? = null,
)

fun JobBidEntity.toDomain() = JobBid(
    id = id,
    jobRequestId = jobRequestId,
    cleanerId = cleanerId,
    price = price,
    createdTime = createdTime,
)
