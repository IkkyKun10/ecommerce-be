package org.riezki.ecommerce.service.auth

import org.riezki.ecommerce.component.JwtUtils
import org.riezki.ecommerce.data.entity.UserEntity
import org.riezki.ecommerce.data.repository.UserRepository
import org.riezki.ecommerce.data.req.LoginRequest
import org.riezki.ecommerce.data.req.RegisterRequest
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val jwtUtils: JwtUtils
) {
    fun register(request: RegisterRequest) : Boolean {
        val userEntity = userRepository.findByPhoneNumber(request.phoneNumber)

        userEntity?.let { return false }

        try {
            val newUser = UserEntity(
                name = request.name,
                phoneNumber = request.phoneNumber,
                password = bCryptPasswordEncoder.encode(request.password)
            )
            userRepository.save(newUser)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun login(request: LoginRequest) : String {
        val authToken = UsernamePasswordAuthenticationToken(
            request.phoneNumber,
            request.password
        )

        authenticationManager.authenticate(authToken)

        val userEntity = userRepository.findByPhoneNumber(request.phoneNumber) ?: throw Exception("User not found")

        return jwtUtils.generateToken(userEntity)
    }
}