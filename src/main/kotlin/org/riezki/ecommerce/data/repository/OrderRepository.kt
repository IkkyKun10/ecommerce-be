package org.riezki.ecommerce.data.repository

import org.riezki.ecommerce.data.entity.OrderEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<OrderEntity, Int> {
    fun findAllByUserId(userId: Int, pageable: Pageable) : Page<OrderEntity>
}