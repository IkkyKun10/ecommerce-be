package org.riezki.ecommerce.data.repository

import org.riezki.ecommerce.data.entity.CartEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CartRepository : JpaRepository<CartEntity, Int> {

    fun findAllByUserId(
        userId: Int,
        pageable: Pageable
    ): Page<CartEntity>

    fun countAllByUserId(userId: Int) : Long

    fun findByUserIdAndProductId(userId: Int, productId: Int) : CartEntity?
}