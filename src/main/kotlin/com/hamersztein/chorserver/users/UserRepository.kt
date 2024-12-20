package com.hamersztein.chorserver.users

import com.hamersztein.chorserver.users.model.UserEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : CoroutineCrudRepository<UserEntity, UUID>
