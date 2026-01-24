package org.riezki.ecommerce.data.req

import jakarta.validation.constraints.NotBlank

data class LoginRequest(
    @field:NotBlank(message = "Phone number cannot be blank")
    val phoneNumber: String,

    @field:NotBlank(message = "Password cannot be blank")
    val password: String
)
