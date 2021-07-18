package com.example.orderservice.dto

data class KafkaOrderDto(
    var schema: Schema? = null,
    var payload: Payload? = null
)
