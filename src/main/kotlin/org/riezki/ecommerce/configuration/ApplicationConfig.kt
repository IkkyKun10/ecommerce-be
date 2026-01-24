package org.riezki.ecommerce.configuration

import org.riezki.ecommerce.service.user.CustomUserDetailService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class ApplicationConfig(
    private val userDetailService: CustomUserDetailService
) {

    @Bean
    fun bcryptPasswordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun authenticationManager(
        httpSecurity: HttpSecurity,
        bCryptPasswordEncoder: BCryptPasswordEncoder
    ) : AuthenticationManager {
        val builder = httpSecurity
            .getSharedObject(AuthenticationManagerBuilder::class.java)
        builder
            .userDetailsService(userDetailService)
            .passwordEncoder(bCryptPasswordEncoder)

        return builder.build()
    }
}