package org.riezki.ecommerce.data.entity

import jakarta.persistence.*

@Entity
@Table(name = "product")
data class ProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val image: String,

    @Column(nullable = false)
    val price: Double,

    @Column(nullable = true)
    val rating: Double? = null,

    @Column(nullable = false)
    val description: String,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    val category: CategoryEntity
)
