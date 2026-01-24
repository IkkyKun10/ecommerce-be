package org.riezki.ecommerce.data.dto

import org.springframework.data.domain.Page

data class PagingInfo<T>(
    val currentPage: Int,
    val nextPage: Int?,
    val previousPage: Int?,
    val totalPages: Int?,
    val totalItems: Long?,
    val content: List<T?>?
) {
    companion object {
        fun <T> convertFromPage(page: Page<T>) : PagingInfo<T> {
            val currentPage = page.number + 1
            val totalPages = page.totalPages
            val previousPage = if (currentPage > totalPages) totalPages else page.number

            return PagingInfo(
                currentPage = currentPage,
                nextPage = if (page.hasNext()) (page.number + 1) + 1 else null,
                previousPage = if (page.hasPrevious()) previousPage else null,
                totalPages = totalPages,
                totalItems = page.totalElements,
                content = page.content
            )
        }
    }
}
