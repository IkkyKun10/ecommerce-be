package org.riezki.ecommerce.data.req

import jakarta.validation.constraints.NotBlank

data class RegisterRequest(
    @field:NotBlank(message = "Phone number cannot be blank")
    val phoneNumber: String,

    @field:NotBlank(message = "Name cannot be blank")
    val name: String,

    @field:NotBlank(message = "Password cannot be blank")
    val password: String
)
