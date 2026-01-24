package org.riezki.ecommerce.data.dto

data class BaseResponse<T>(
    val message: String,
    val status: Boolean,
    val data: T? = null
)