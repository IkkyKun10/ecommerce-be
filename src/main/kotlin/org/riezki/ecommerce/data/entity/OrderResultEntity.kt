package org.riezki.ecommerce.data.entity

import jakarta.persistence.*

@Table(name = "order_result")
@Entity
data class OrderResultEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(name = "payment_url")
    val paymentUrl: String,

    @Column(name = "expired_at")
    val expiredAt: String
)
