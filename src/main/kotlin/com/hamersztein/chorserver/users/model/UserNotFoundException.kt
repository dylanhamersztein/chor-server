package com.hamersztein.chorserver.users.model

import java.util.*

class UserNotFoundException(userId: UUID) : RuntimeException("User with id [$userId] not found")
