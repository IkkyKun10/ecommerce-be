package org.riezki.ecommerce.data.req

import jakarta.validation.constraints.NotBlank

data class CartRequest(
    @field:NotBlank(message = "Product ID cannot be blank")
    val productId: Int,
    @field:NotBlank(message = "Quantity cannot be blank")
    val quantity: Int
)
