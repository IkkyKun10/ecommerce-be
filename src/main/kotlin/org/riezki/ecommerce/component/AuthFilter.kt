package org.riezki.ecommerce.component

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.riezki.ecommerce.service.user.CustomUserDetailService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class AuthFilter(
    private val jwtUtils: JwtUtils,
    private val customUserDetailService: CustomUserDetailService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            if (request.servletPath?.contains("/api/v1/auth") == true) {
                filterChain.doFilter(request, response)
                return
            }

            val authHeader = request.getHeader("Authorization")

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response)
                return
            }

            val token = authHeader.split(" ")[1].trim()
            val phoneNumber = jwtUtils.extractClaim(token) { it.subject }

            if (phoneNumber != null && SecurityContextHolder.getContext().authentication == null) {
                val userDetails = customUserDetailService.loadUserByUsername(phoneNumber)

                if (jwtUtils.isValidToken(token, userDetails ?: return)) {
                    val authToken = UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.authorities
                    )

                    val webAuthDetails = WebAuthenticationDetailsSource().buildDetails(request)
                    authToken.details = webAuthDetails
                    SecurityContextHolder.getContext().authentication = authToken
                }
            }

            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            println("Error ----------> ${e.message}")
        }
    }
}