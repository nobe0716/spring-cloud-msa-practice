package com.example.orderservice.controller

import com.example.orderservice.Log
import com.example.orderservice.dto.OrderDto
import com.example.orderservice.messagequeue.KafkaProducer
import com.example.orderservice.messagequeue.OrderProducer
import com.example.orderservice.service.OrderService
import com.example.orderservice.vo.RequestOrder
import com.example.orderservice.vo.ResponseOrder
import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class OrderController(
    val environment: Environment,
    val orderService: OrderService,
    val kafkaProducer: KafkaProducer,
    val orderProducer: OrderProducer
) {
    companion object : Log

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{userId}/orders")
    fun createOrder(@PathVariable userId: String, @RequestBody requestOrder: RequestOrder): ResponseOrder {
        val mapper = ModelMapper()
        mapper.configuration.matchingStrategy = MatchingStrategies.STRICT
        val orderDto: OrderDto = mapper.map(requestOrder, OrderDto::class.java)
        orderDto.userId = userId

        // JPA
//        orderDto = orderService.createOrder(orderDto)
//        val responseOrder = mapper.map(orderDto, ResponseOrder::class.java)

        // Kafka
        orderDto.orderId = UUID.randomUUID().toString()
        orderDto.totalPrice = requestOrder.qty!! * requestOrder.unitPrice!!

        // send this order to the kafka
        orderProducer.send("orders", orderDto)
        kafkaProducer.send("example-catalog-topic", orderDto)

        return mapper.map(orderDto, ResponseOrder::class.java)
    }

    @GetMapping("/{userId}/orders")
    fun getOrders(@PathVariable userId: String): List<ResponseOrder> {
        val catalogByAll = orderService.getOrdersByUserId(userId)
        val mapper = ModelMapper()
        mapper.configuration.matchingStrategy = MatchingStrategies.STRICT
        return catalogByAll.map { mapper.map(it, ResponseOrder::class.java) }
            .toList()
    }

    @GetMapping("/orders/{orderId}")
    fun getOrder(@PathVariable orderId: String): ResponseOrder {
        val mapper = ModelMapper()
        mapper.configuration.matchingStrategy = MatchingStrategies.STRICT
        return mapper.map(orderService.getOrderByOrderId(orderId), ResponseOrder::class.java)
    }

    @GetMapping("/health_check")
    fun status(): String {
        return "It's working in Catalog Service on port ${environment.getProperty("server.port")}"
    }
}