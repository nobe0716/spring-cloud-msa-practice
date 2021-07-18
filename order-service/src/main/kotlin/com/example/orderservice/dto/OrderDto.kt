package com.example.orderservice.dto

data class OrderDto(
    var productId: String? = null,
    var qty: Int = 0,
    var unitPrice: Int = 0,
    var totalPrice: Int = 0,
    var orderId: String? = null,
    var userId: String? = null,
)
