package org.riezki.ecommerce.controller.controller

import org.riezki.ecommerce.data.dto.BaseResponse
import org.riezki.ecommerce.data.dto.PagingInfo
import org.riezki.ecommerce.data.entity.CategoryEntity
import org.riezki.ecommerce.service.category.CategoryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/categories")
class CategoryController(
    private val service: CategoryService
) {

    @GetMapping
    fun getCategories(
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ) : BaseResponse<PagingInfo<CategoryEntity>> {

        val category = service.getAllCategories(page, size)
        return BaseResponse(message = "Success", status = true, data = category)
    }
}