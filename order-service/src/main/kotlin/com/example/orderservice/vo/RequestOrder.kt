package com.example.orderservice.vo

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

class RequestOrder {
    var productId: @NotNull(message = "productId should not be null") String? = null
    var qty: @Min(value = 1) Int? = null
    var unitPrice: @Min(value = 1) Int? = null
}