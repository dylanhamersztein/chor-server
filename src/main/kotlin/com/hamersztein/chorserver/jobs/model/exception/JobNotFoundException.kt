package com.hamersztein.chorserver.jobs.model.exception

import java.util.UUID

class JobNotFoundException(jobId: UUID) : RuntimeException("Job with id [$jobId] not found")
