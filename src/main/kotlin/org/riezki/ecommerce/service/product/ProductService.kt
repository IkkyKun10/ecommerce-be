package org.riezki.ecommerce.service.product

import org.riezki.ecommerce.data.dto.BaseResponse
import org.riezki.ecommerce.data.dto.PagingInfo
import org.riezki.ecommerce.data.entity.ProductEntity
import org.riezki.ecommerce.data.repository.ProductRepository
import org.riezki.ecommerce.data.utils.ProductSortType
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository
) {

    fun getProducts(
        page: Int,
        size: Int,
        sort: String?
    ): BaseResponse<PagingInfo<ProductEntity>> {
        val pageNumber = if (page < 1) 1 else page
        var pageRequest = PageRequest.of(pageNumber - 1, size)
        if (!sort.isNullOrEmpty()) {
            val sortType = mapStringToSort(sort)
            pageRequest = pageRequest.withSort(sortType)
        }
        val products = productRepository.findAll(pageRequest)
        return BaseResponse(
            message = "Success",
            status = true,
            data = PagingInfo.convertFromPage(products)
        )
    }

    fun getProductsByNameAndDescription(
        page: Int,
        size: Int,
        query: String,
        sort: String?
    ): BaseResponse<PagingInfo<ProductEntity>> {
        val pageNumber = if (page < 1) 1 else page
        var pageRequest = PageRequest.of(pageNumber - 1, size)
        if (!sort.isNullOrEmpty()) {
            val sortType = mapStringToSort(sort)
            pageRequest = pageRequest.withSort(sortType)
        }
        val products = productRepository.findByNameOrDescriptionContainsIgnoreCase(
            query,
            query,
            pageRequest
        )
        return BaseResponse(
            message = "Success",
            status = true,
            data = PagingInfo.convertFromPage(products)
        )
    }

    fun getProducts(
        page: Int,
        size: Int,
        query: String,
        sort: String?
    ): BaseResponse<PagingInfo<ProductEntity>> {
        val pageNumber = if (page < 1) 1 else page
        var pageRequest = PageRequest.of(pageNumber - 1, size)
        if (!sort.isNullOrEmpty()) {
            val sortType = mapStringToSort(sort)
            pageRequest = pageRequest.withSort(sortType)
        }
        val products = productRepository.filter(query, pageRequest)
        return BaseResponse(
            message = "Success",
            status = true,
            data = PagingInfo.convertFromPage(products)
        )
    }

    fun getProducts(
        page: Int,
        size: Int,
        categoryId: Int,
        sort: String?
    ): BaseResponse<PagingInfo<ProductEntity>> {
        val pageNumber = if (page < 1) 1 else page
        var pageRequest = PageRequest.of(pageNumber - 1, size)
        if (!sort.isNullOrEmpty()) {
            val sortType = mapStringToSort(sort)
            pageRequest = pageRequest.withSort(sortType)
        }
        val products = productRepository.filter(categoryId, pageRequest)
        return BaseResponse(
            message = "Success",
            status = true,
            data = PagingInfo.convertFromPage(products)
        )
    }

    fun getProducts(
        page: Int,
        size: Int,
        query: String,
        categoryId: Int,
        sort: String?
    ): BaseResponse<PagingInfo<ProductEntity>> {
        val pageNumber = if (page < 1) 1 else page
        var pageRequest = PageRequest.of(pageNumber - 1, size)
        if (!sort.isNullOrEmpty()) {
            val sortType = mapStringToSort(sort)
            pageRequest = pageRequest.withSort(sortType)
        }
        val products = productRepository.filter(query, categoryId, pageRequest)

        return BaseResponse(
            message = "Success",
            status = true,
            data = PagingInfo.convertFromPage(products)
        )
    }

    private fun mapStringToSort(value: String): Sort {
        val productSort = ProductSortType.fromValue(value)
        return when (productSort) {
            ProductSortType.PRICE_ASC -> Sort.by(Sort.Direction.ASC, "price")
            ProductSortType.PRICE_DESC -> Sort.by(Sort.Direction.DESC, "price")
        }
    }
}