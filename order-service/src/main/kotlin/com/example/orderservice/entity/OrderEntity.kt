package com.example.orderservice.entity

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "orders")
class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false, length = 120)
    var productId: String? = null

    @Column(nullable = false)
    var qty: Int? = null

    @Column(nullable = false)
    var unitPrice: Int? = null

    @Column(nullable = false)
    var totalPrice: Int? = null

    @Column(nullable = false)
    var userId: String? = null

    @Column(nullable = false, unique = true)
    var orderId: String? = null

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: Date? = null
}