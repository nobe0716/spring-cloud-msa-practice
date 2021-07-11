package com.example.userservice.client

import com.example.userservice.vo.ResponseOrder
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient("order-service")
interface OrderServiceClient {
    @GetMapping("/{userId}/orders")
    fun getOrders(@PathVariable userId: String): List<ResponseOrder>
}