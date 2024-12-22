package com.hamersztein.chorserver.users.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import java.util.*

data class User(
    @Schema(description = "User ID. Must be null when creating user.")
    val id: UUID? = null,

    @Schema(description = "User first name")
    @field:NotEmpty
    @field:Size(max = 30)
    val firstName: String,

    @Schema(description = "User middle names")
    @field:Size(max = 30)
    val middleNames: String? = null,

    @Schema(description = "User last name")
    @field:NotEmpty
    @field:Size(max = 50)
    val lastName: String,

    @Schema(description = "Address line 1")
    @field:NotEmpty
    @field:Size(max = 70)
    val addressLine1: String,

    @Schema(description = "Address line 2")
    @field:Size(max = 70)
    val addressLine2: String? = null,

    @Schema(description = "Town")
    @field:Size(max = 30)
    val town: String? = null,

    @Schema(description = "City")
    @field:Size(max = 30)
    val city: String? = null,

    @Schema(description = "Postal code")
    @field:NotEmpty
    @field:Size(max = 10)
    val postCode: String,

    @Schema(description = "Whether the user is active or not. Must be null when creating user.")
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
