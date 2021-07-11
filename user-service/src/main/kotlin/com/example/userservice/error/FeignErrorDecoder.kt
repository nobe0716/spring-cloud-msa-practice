package com.example.userservice.error

import com.example.userservice.Log
import feign.Response
import feign.codec.ErrorDecoder
import org.apache.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class FeignErrorDecoder: ErrorDecoder {
    companion object: Log

    override fun decode(methodKey: String?, response: Response?): Exception {
        when (response!!.status()) {
            HttpStatus.SC_NOT_FOUND -> {
                if (methodKey!!.contains("getOrders")) {
                    return ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "User's order is empty")
                }
            }
//            HttpStatus.SC_BAD_REQUEST,
            else -> return Exception(response.reason())
        }
        return java.lang.Exception(response.reason())
    }
}