package org.riezki.ecommerce.controller.cart

import org.riezki.ecommerce.data.dto.BaseResponse
import org.riezki.ecommerce.data.dto.CartDto
import org.riezki.ecommerce.data.entity.CartEntity
import org.riezki.ecommerce.data.req.CartRequest
import org.riezki.ecommerce.service.cart.CartService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/carts")
class CartController(
    private val cartService: CartService
) {

    @GetMapping
    fun getCarts() : BaseResponse<CartDto> {
        return BaseResponse(
            message = "Success",
            status = true,
            data = cartService.getAllCarts()
        )
    }

    @PostMapping
    fun addToChart(
        @RequestBody request: CartRequest
    ) : BaseResponse<CartEntity> {
        val cart = cartService.addToCart(request.productId, request.quantity)
        return BaseResponse(
            message = "Success",
            status = true,
            data = cart
        )
    }

    @DeleteMapping
    fun deleteCart(
        @RequestBody request: CartRequest
    ) : BaseResponse<Boolean> {
        val result = cartService.deleteCart(request.productId, request.quantity)
        return BaseResponse(
            message = "Success",
            status = true,
            data = result
        )
    }
}