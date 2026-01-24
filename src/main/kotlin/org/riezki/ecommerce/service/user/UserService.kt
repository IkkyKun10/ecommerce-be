package org.riezki.ecommerce.service.user

import org.riezki.ecommerce.data.entity.UserEntity
import org.riezki.ecommerce.data.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun getUserEntity() : UserEntity {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val phoneNumber = userDetails.username

        return userRepository.findByPhoneNumber(phoneNumber) ?: throw Exception("User not found")
    }

}