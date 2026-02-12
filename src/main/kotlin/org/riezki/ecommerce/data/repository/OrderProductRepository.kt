package org.riezki.ecommerce.data.repository

import org.riezki.ecommerce.data.entity.OrderProductEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderProductRepository : JpaRepository<OrderProductEntity, Int> {
}