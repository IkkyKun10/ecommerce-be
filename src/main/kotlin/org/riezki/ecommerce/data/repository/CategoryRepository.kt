package org.riezki.ecommerce.data.repository

import org.riezki.ecommerce.data.entity.CategoryEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<CategoryEntity, Int> {

    override fun findAll(pageable: Pageable) : Page<CategoryEntity>
}