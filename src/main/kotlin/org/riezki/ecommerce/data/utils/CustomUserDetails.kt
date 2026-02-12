package org.riezki.ecommerce.data.utils

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class CustomUserDetails(
    val id: Int = 0,
    val name: String = "",
    private val username: String = "",
    private val password: String = "",
    private val authorities: Collection<GrantedAuthority> = emptyList()
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority> = authorities

    override fun getPassword(): String = password

    override fun getUsername(): String = username
/*
    // Spring Security memerlukan method ini untuk bernilai true agar user bisa login
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true*/
}
