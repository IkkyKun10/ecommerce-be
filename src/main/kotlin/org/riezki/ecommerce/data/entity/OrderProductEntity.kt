package org.riezki.ecommerce.data.entity

import jakarta.persistence.*

@Table(name = "order_product", schema = "public")
@Entity
data class OrderProductEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(name = "product_id")
    val productId: Int,

    val name: String,

    val image: String,

    val price: Double,

    val quantity: Int
)
