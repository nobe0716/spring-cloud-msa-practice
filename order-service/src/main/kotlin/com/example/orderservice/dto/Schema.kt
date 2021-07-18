package com.example.orderservice.dto

data class Schema(
    var type: String? = null,
    var fields: List<Field> = emptyList(),
    var optional: Boolean? = null,
    var name: String? = null,
)
