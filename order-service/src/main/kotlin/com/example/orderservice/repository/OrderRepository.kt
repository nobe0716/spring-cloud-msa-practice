package com.example.orderservice.repository

import com.example.orderservice.entity.OrderEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : CrudRepository<OrderEntity, Long> {
    fun findByOrderId(orderId: String): OrderEntity
    fun findByUserId(userId: String): MutableIterable<OrderEntity>
}