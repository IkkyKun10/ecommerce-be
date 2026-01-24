package org.riezki.ecommerce.data.dto

data class CartDto(
    val amount: Double,
    val products: List<ProductCartDto>
)
