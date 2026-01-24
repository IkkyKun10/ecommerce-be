package org.riezki.ecommerce.controller.product

import org.riezki.ecommerce.data.dto.BaseResponse
import org.riezki.ecommerce.data.dto.PagingInfo
import org.riezki.ecommerce.data.entity.ProductEntity
import org.riezki.ecommerce.service.product.ProductService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/products")
class ProductController(
    private val productService: ProductService
) {

    @GetMapping
    fun getProducts(
        @RequestParam(value = "page", defaultValue = "1") page: Int,
        @RequestParam(value = "size", defaultValue = "10") size: Int,
        @RequestParam(required = false, value = "q") query: String?,
        @RequestParam(required = false, value = "categoryId") categoryId: Int?,
        @RequestParam(required = false, value = "sort") sort: String?
    ) : BaseResponse<PagingInfo<ProductEntity>> {

        val key = (if (query == null) "NQ" else "Q") + (if (categoryId == null) "NC" else "C")

        when (key) {
            "NQNC" -> return productService.getProducts(page, size, sort)
            "NQC" -> return productService.getProducts(page, size, categoryId!!, sort)
            "QNC" -> return productService.getProducts(page, size, query!!, sort)
            "QC" -> return productService.getProducts(page, size, query!!, categoryId!!, sort)
        }

        return BaseResponse(
            message = "Invalid Request",
            status = false,
            data = null
        )
    }
}