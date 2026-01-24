package org.riezki.ecommerce.service.category

import org.riezki.ecommerce.data.dto.PagingInfo
import org.riezki.ecommerce.data.entity.CategoryEntity
import org.riezki.ecommerce.data.repository.CategoryRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val repository: CategoryRepository
) {

    fun getAllCategories(page: Int, size: Int) : PagingInfo<CategoryEntity> {
        val pageNumber = if (page < 1) 1 else page
        val pageRequest = PageRequest.of(pageNumber - 1, size)
        val categories = repository.findAll(pageRequest)
        return PagingInfo.convertFromPage(categories)
    }
}