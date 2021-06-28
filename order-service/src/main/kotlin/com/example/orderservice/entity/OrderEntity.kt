package com.example.orderservice.entity

import org.hibernate.annotations.ColumnDefault
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "orders")
class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false, length = 120, unique = true)
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

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    var createdAt: Date? = null
}