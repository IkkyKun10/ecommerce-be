package org.riezki.ecommerce.data.repository

import org.riezki.ecommerce.data.entity.OrderResultEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderResultRepository : JpaRepository<OrderResultEntity, Int> {
}