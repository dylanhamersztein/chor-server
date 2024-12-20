package com.hamersztein.chorserver.users.model

import java.util.*

class CannotUpdateInactiveUserException(userId: UUID) :
    Throwable("User with id [$userId] is inactive and cannot be updated")
