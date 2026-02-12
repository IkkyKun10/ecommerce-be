package org.riezki.ecommerce.service.user

import org.riezki.ecommerce.data.entity.UserEntity
import org.riezki.ecommerce.data.repository.UserRepository
import org.riezki.ecommerce.data.utils.CustomUserDetails
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Service
class CustomUserDetailService(
    private val userRepository: UserRepository
) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): CustomUserDetails? {
        return userRepository
            .findByPhoneNumber(username)
            ?.let { mapFromEntity(it) } ?: throw UsernameNotFoundException("User not found with username: $username")
    }

    private fun mapFromEntity(userEntity: UserEntity) : CustomUserDetails {
        return CustomUserDetails(
            id = userEntity.id,
            name = userEntity.name,
            username = userEntity.phoneNumber,
            password = userEntity.password,
            /*authorities = User.builder().username(userEntity.phoneNumber).password(userEntity.password).roles("USER").build().authorities*/
            authorities = emptyList()
        )
    }
}