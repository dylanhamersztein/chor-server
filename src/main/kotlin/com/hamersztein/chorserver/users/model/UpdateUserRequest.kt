package com.hamersztein.chorserver.users.model

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "At least one field must be populated.")
data class UpdateUserRequest(
    val firstName: String? = null,
    val middleNames: String? = null,
    val lastName: String? = null,
    val addressLine1: String? = null,
    val addressLine2: String? = null,
    val town: String? = null,
    val city: String? = null,
    val postCode: String? = null,
)
