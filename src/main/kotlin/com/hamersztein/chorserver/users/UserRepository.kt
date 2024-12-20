package com.hamersztein.chorserver.users

import com.hamersztein.chorserver.users.model.UserEntity
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : CoroutineCrudRepository<UserEntity, UUID> {

    @Query("update users set active = :active where id = :userId returning *")
    fun updateUserActive(userId: UUID, active: Boolean): UserEntity
}
