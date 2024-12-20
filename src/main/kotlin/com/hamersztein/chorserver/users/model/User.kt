package com.hamersztein.chorserver.users.model

import java.util.*

data class User(
    val id: UUID? = null,
    val firstName: String,
    val middleNames: String? = null,
    val lastName: String,
    val addressLine1: String,
    val addressLine2: String? = null,
    val town: String? = null,
    val city: String? = null,
    val postCode: String,
    val active: Boolean = true,
)

fun User.toEntity() = UserEntity(
    id = id,
    firstName = firstName,
    middleNames = middleNames,
    lastName = lastName,
    addressLine1 = addressLine1,
    addressLine2 = addressLine2,
    town = town,
    city = city,
    postCode = postCode,
    active = active,
)
