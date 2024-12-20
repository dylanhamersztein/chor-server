package com.hamersztein.chorserver.users.model

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
