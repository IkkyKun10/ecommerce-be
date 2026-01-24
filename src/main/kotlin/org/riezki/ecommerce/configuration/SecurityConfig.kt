package org.riezki.ecommerce.configuration

import org.riezki.ecommerce.component.AuthFilter
import org.riezki.ecommerce.component.ExceptionUtils
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import kotlin.jvm.Throws

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val authFilter: AuthFilter,
    private val exceptionUtils: ExceptionUtils
) {

    private val whiteList = arrayOf(
        "/api/v1/auth/login",
        "/api/v1/auth/register",
        "/api/v1/products",
        "/api/v1/categories",
    )

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(httpSecurity: HttpSecurity) : SecurityFilterChain {
        httpSecurity.csrf { it.disable() }
            .authorizeHttpRequests { authRequest ->
                authRequest
                    .requestMatchers(*whiteList)
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            }
            .sessionManagement { managementConfigurer ->
                managementConfigurer
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .exceptionHandling { exceptionHandlingConfigurer ->
                exceptionHandlingConfigurer
                    .accessDeniedHandler { request, response, exception ->
                        println("error accessDeniedHandler ---------> ${exception.message}")
                        exceptionUtils.sendExceptionEntryPoint(request, response, exception)
                    }
                    .authenticationEntryPoint { request, response, exception ->
                        println("error authenticationEntryPoint ---------> ${exception.message}")
                        exceptionUtils.sendExceptionEntryPoint(request, response, exception)
                    }
            }
            .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter::class.java)

        return httpSecurity.build()
    }
}