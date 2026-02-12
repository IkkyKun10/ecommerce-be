package org.riezki.ecommerce.data.repository

import org.riezki.ecommerce.data.entity.CartEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface CartRepository : JpaRepository<CartEntity, Int> {

    fun findAllByUserIdOrderByCreatedAtDesc(
        userId: Int,
        pageable: Pageable
    ): Page<CartEntity>

    fun countAllByUserId(userId: Int) : Long

    fun findByUserIdAndProductId(userId: Int, productId: Int) : CartEntity?

    @Modifying
    @Transactional
    @Query("""
        DELETE FROM CartEntity c WHERE 
        c.userId = :userId AND c.product.id IN :productIds
    """)
    fun deleteByUserIdAndProductIds(userId: Int, productIds: List<Int>)
}