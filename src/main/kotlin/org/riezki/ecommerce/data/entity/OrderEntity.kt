package org.riezki.ecommerce.data.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Table(name = "order", schema = "public")
@Entity
data class OrderEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,

    @Column(name = "user_id")
    val userId: Int,

    @Column
    val status: String,

    @Column
    val amount: Double,

    @Column(name = "created_at")
    val createdAt: String,

    @Column(name = "updated_at")
    val updatedAt: String,

    @OneToMany
    @JoinColumn(
        name = "order_id", referencedColumnName = "id"
    )
    val products: Set<OrderProductEntity> = mutableSetOf(),

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_result_id")
    val result: OrderResultEntity? = null
)
