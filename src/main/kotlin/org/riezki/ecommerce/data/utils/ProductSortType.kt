package org.riezki.ecommerce.data.utils

enum class ProductSortType(val value: String) {
    PRICE_ASC("price_asc"),
    PRICE_DESC("price_desc");

    companion object {
        fun fromValue(value: String) : ProductSortType {
            return ProductSortType
                .entries
                .firstOrNull {
                    it.value.equals(value, ignoreCase = true)
                } ?: throw IllegalArgumentException("Invalid sort type: $value")
        }
    }
}