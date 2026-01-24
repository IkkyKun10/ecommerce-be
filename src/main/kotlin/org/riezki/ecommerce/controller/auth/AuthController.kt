package org.riezki.ecommerce.controller.auth

import org.riezki.ecommerce.data.dto.BaseResponse
import org.riezki.ecommerce.data.req.LoginRequest
import org.riezki.ecommerce.data.req.RegisterRequest
import org.riezki.ecommerce.service.auth.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/register")
    fun register(
        @RequestBody request: RegisterRequest
    ) : BaseResponse<Boolean> {
        val result = authService.register(request)

        return BaseResponse(
            message = "Success",
            status = true,
            data = result
        )
    }

    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginRequest
    ) : BaseResponse<String> {
        val token = authService.login(request)
        return BaseResponse(message = "Success", status = true, data = token)
    }
}