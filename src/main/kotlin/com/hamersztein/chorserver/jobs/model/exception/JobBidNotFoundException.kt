package com.hamersztein.chorserver.jobs.model.exception

import java.util.UUID

class JobBidNotFoundException(jobId: UUID) : RuntimeException("Job bid with id [$jobId] not found")
