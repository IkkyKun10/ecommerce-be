package org.riezki.ecommerce.data.repository

import org.riezki.ecommerce.data.entity.ProductEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<ProductEntity, Int> {

    override fun findAll(pageable: Pageable) : Page<ProductEntity>

    fun findByNameOrDescriptionContainsIgnoreCase(name: String, description: String, pageable: Pageable) : Page<ProductEntity>

    @Query(
        """
            SELECT p FROM ProductEntity p
            WHERE (LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%')))
        """)
    fun filter(
        query: String,
        pageable: Pageable
    ) : Page<ProductEntity>

    @Query(
        """
            SELECT p FROM ProductEntity p
            WHERE (LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :query, '%'))) AND
            p.category.id = :categoryId
        """)
    fun filter(
        query: String,
        categoryId: Int,
        pageable: Pageable
    ) : Page<ProductEntity>

    @Query(
        """
            SELECT p FROM ProductEntity p WHERE 
            p.category.id = :categoryId
        """)
    fun filter(
        categoryId: Int,
        pageable: Pageable
    ) : Page<ProductEntity>
}