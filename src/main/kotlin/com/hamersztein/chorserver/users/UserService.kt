package com.hamersztein.chorserver.users

import com.hamersztein.chorserver.users.model.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class UserService(private val userRepository: UserRepository) {
    suspend fun createUser(user: User) = userRepository.save(user.toEntity()).toUser()

    suspend fun getUser(userId: UUID) = userRepository.findById(userId)?.toUser() ?: throw UserNotFoundException(userId)

    suspend fun updateUserActive(userId: UUID, active: Boolean): User {
        val user = getUser(userId)
        val inactiveUser = user.copy(active = active)

        return userRepository.save(inactiveUser.toEntity()).toUser()
    }

    suspend fun updateUser(userId: UUID, updateUserRequest: UpdateUserRequest): User {
        val user = getUser(userId).takeIf { it.active } ?: throw CannotUpdateInactiveUserException(userId)

        val updatedUser = User(
            id = userId,
            firstName = updateUserRequest.firstName ?: user.firstName,
            middleNames = updateUserRequest.middleNames ?: user.middleNames,
            lastName = updateUserRequest.lastName ?: user.lastName,
            addressLine1 = updateUserRequest.addressLine1 ?: user.addressLine1,
            addressLine2 = updateUserRequest.addressLine2 ?: user.addressLine2,
            town = updateUserRequest.town ?: user.town,
            city = updateUserRequest.city ?: user.city,
            postCode = updateUserRequest.postCode ?: user.postCode,
        )

        return userRepository.save(updatedUser.toEntity()).toUser()
    }
}
