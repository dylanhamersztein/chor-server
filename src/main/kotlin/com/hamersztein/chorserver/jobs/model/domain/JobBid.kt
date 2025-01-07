package com.hamersztein.chorserver.jobs.model.domain

import com.hamersztein.chorserver.jobs.model.entity.JobBidEntity
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.util.UUID

data class JobBid(
    val id: UUID? = null,
    val jobRequestId: UUID,
    val cleanerId: UUID,
    val price: BigDecimal,
    val createdTime: OffsetDateTime? = null,
)

fun JobBid.toEntity() = JobBidEntity(
    id = id,
    jobRequestId = jobRequestId,
    cleanerId = cleanerId,
    price = price,
    createdTime = createdTime
)
