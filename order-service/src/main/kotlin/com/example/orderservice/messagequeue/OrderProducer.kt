package com.example.orderservice.messagequeue

import com.example.orderservice.Log
import com.example.orderservice.dto.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class OrderProducer(val kafkaTemplate: KafkaTemplate<String, String>) {
    companion object : Log

    private val objectMapper = ObjectMapper()

    init {
        objectMapper.propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE
    }
    private val schema = Schema(
        type = "struct",
        fields = listOf(
            Field(type = "string", optional = true, field = "order_id"),
            Field(type = "string", optional = true, field = "user_id"),
            Field(type = "string", optional = true, field = "product_id"),
            Field(type = "int32", optional = true, field = "qty"),
            Field(type = "int32", optional = true, field = "unit_price"),
            Field(type = "int32", optional = true, field = "total_price"),
        )
    )

    fun send(topic: String, orderDto: OrderDto): KafkaOrderDto {
        val kafkaOrderDto = KafkaOrderDto(
            schema = schema,
            payload = Payload(
                orderId = orderDto.orderId,
                userId = orderDto.userId,
                productId = orderDto.productId,
                qty = orderDto.qty,
                unitPrice = orderDto.unitPrice,
                totalPrice = orderDto.totalPrice
            )
        )
        val kafkaOrderDtoAsStr = objectMapper.writeValueAsString(kafkaOrderDto)
        kafkaTemplate.send(topic, kafkaOrderDtoAsStr)
        log.info("Order Producer sent data from Order microservice: {}", kafkaOrderDtoAsStr)
        return kafkaOrderDto
    }
}