package com.hamersztein.chorserver.users.model

import java.util.*

class UserNotFoundException(userId: UUID) : Throwable("User with id [$userId] not found")
