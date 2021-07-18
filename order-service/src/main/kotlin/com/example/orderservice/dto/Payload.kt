package com.example.orderservice.dto

import com.fasterxml.jackson.annotation.JsonAlias

data class Payload(
    @JsonAlias("order_id")
    var orderId: String? = null,
    @JsonAlias("user_id")
    var userId: String? = null,
    @JsonAlias("product_id")
    var productId: String? = null,
    var qty: Int = 0,
    @JsonAlias("unit_price")
    var unitPrice: Int = 0,
    @JsonAlias("total_price")
    var totalPrice: Int = 0,
)
