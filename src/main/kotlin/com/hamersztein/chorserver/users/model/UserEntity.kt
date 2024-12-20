package com.hamersztein.chorserver.users.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("users")
data class UserEntity(
    @Id val id: UUID? = null,
    val firstName: String,
    val middleNames: String? = null,
    val lastName: String,
    @Column("address_line_1") val addressLine1: String,
    @Column("address_line_2") val addressLine2: String? = null,
    val town: String? = null,
    val city: String? = null,
    val postCode: String,
    val active: Boolean = true,
)

fun UserEntity.toUser() = User(
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
