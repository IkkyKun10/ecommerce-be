package org.riezki.ecommerce.data.entity

import jakarta.persistence.*
import java.sql.Date
import java.time.Instant

@Table(name = "cart", schema = "public")
@Entity
data class CartEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(nullable = false, name = "user_id")
    val userId: Int,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    val product: ProductEntity,

    @Column(nullable = false)
    var quantity: Int = 0,

    @Column(nullable = false, name = "created_at")
    var createdAt: Long,

    @Column(nullable = false, name = "updated_at")
    var updatedAt: Long
)
