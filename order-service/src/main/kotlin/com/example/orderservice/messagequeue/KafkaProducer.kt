package com.example.orderservice.messagequeue

import com.example.orderservice.Log
import com.example.orderservice.dto.OrderDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaProducer(val kafkaTemplate: KafkaTemplate<String, String>) {
    companion object : Log

    private val objectMapper = ObjectMapper()

    fun send(topic: String, orderDto: OrderDto): OrderDto {
        val orderAsStr = objectMapper.writeValueAsString(orderDto)
        kafkaTemplate.send(topic, orderAsStr)
        log.info("Kafka Producer sent data from Order microservice: {}", orderDto)
        return orderDto
    }
}