package org.riezki.ecommerce.data.dto

data class ProductCartDto(
    val id: Int,
    val name: String,
    val price: Double,
    val image: String,
    val quantity: Int,
    val createdAt: String,
    val updatedAt: String
)
