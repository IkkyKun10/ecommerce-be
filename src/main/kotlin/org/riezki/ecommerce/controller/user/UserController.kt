package org.riezki.ecommerce.controller.user

import org.riezki.ecommerce.data.dto.BaseResponse
import org.riezki.ecommerce.data.entity.UserEntity
import org.riezki.ecommerce.data.req.RegisterRequest
import org.riezki.ecommerce.service.user.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping
    fun getUser() : BaseResponse<UserEntity> {
        val user = userService.getUserEntity()
        return BaseResponse("Success", true, user)
    }
}