package org.riezki.ecommerce.service.cart

import org.riezki.ecommerce.data.dto.CartDto
import org.riezki.ecommerce.data.dto.ProductCartDto
import org.riezki.ecommerce.data.entity.CartEntity
import org.riezki.ecommerce.data.repository.CartRepository
import org.riezki.ecommerce.data.repository.ProductRepository
import org.riezki.ecommerce.data.utils.CustomUserDetails
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode
import java.sql.Date
import java.time.Instant

@Service
class CartService(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository
) {

    fun getAllCarts() : CartDto {
        val pageable = PageRequest.of(0, Int.MAX_VALUE)
        val customUserDetails = SecurityContextHolder
            .getContext()
            .authentication
            .principal as CustomUserDetails
        val userId = customUserDetails.id
        val carts = cartRepository.findAllByUserIdOrderByCreatedAtDesc(userId, pageable).toList()

        val amount = carts.sumOf { it.product.price * it.quantity }
        val roundedAmount = roundPrice(amount)

        val cartDto = CartDto(
            amount = roundedAmount,
            products = carts.map { cart ->
                ProductCartDto(
                    id = cart.product.id,
                    name = cart.product.name,
                    price = roundPrice(cart.product.price),
                    image = cart.product.image,
                    quantity = cart.quantity,
                    createdAt = cart.createdAt.toString(),
                    updatedAt = cart.updatedAt.toString()
                )
            }
        )

        return cartDto
    }

    fun addToCart(productId: Int, quantity: Int) : CartEntity {
        val userDetails = SecurityContextHolder
            .getContext()
            .authentication
            .principal as CustomUserDetails

        val userId = userDetails.id
        val product = productRepository.findById(productId).orElseThrow {
            throw IllegalArgumentException("Product not found")
        }

        val cartExist = cartRepository.findByUserIdAndProductId(userId, productId)
        if (cartExist != null) {
            cartExist.quantity += quantity
            cartExist.updatedAt = Instant.now().toEpochMilli()
            return cartRepository.save(cartExist)
        }

        val cart = CartEntity(
            userId = userId,
            product = product,
            quantity = quantity,
            createdAt = Instant.now().toEpochMilli(),
            updatedAt = Instant.now().toEpochMilli()
        )

        return cartRepository.save(cart)
    }

    fun deleteCart(productId: Int, quantity: Int) : Boolean {
        val userDetails = SecurityContextHolder
            .getContext()
            .authentication
            .principal as CustomUserDetails

        val userId = userDetails.id
        val cartExist = cartRepository.findByUserIdAndProductId(userId, productId)
            ?: throw IllegalArgumentException("Cart not found")

        val currentQuantity = cartExist.quantity
        val newQuantity = currentQuantity - quantity

        if (newQuantity < 0) {
            throw IllegalArgumentException("Quantity is Invalid!")
        }

        if (newQuantity == 0) {
            cartRepository.delete(cartExist)
        } else {
            cartExist.quantity = newQuantity
            cartExist.updatedAt = Instant.now().toEpochMilli()
            cartRepository.save(cartExist)
        }

        return true
    }

    private fun roundPrice(price: Double) : Double {
        return BigDecimal(price).setScale(2, RoundingMode.HALF_UP).toDouble()
    }
}