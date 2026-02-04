package org.riezki.ecommerce.data.req

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.validation.constraints.NotBlank

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class OrderExecuteRequest(
    @field:NotBlank(message = "Order ID cannot be blank")
    val orderId: Int,

    @field:NotBlank(message = "Password cannot be blank")
    val password: String
)
