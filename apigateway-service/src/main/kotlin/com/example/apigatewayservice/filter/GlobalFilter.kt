package com.example.apigatewayservice.filter

import com.example.apigatewayservice.Log
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class GlobalFilter : AbstractGatewayFilterFactory<GlobalFilter.Config>(Config::class.java) {

    companion object : Log
    class Config(val baseMessage: String, val preLogger: Boolean, val postLogger: Boolean)

    override fun apply(config: Config?): GatewayFilter {
        // Custom Pre Filter
        return GatewayFilter { exchange: ServerWebExchange, chain: GatewayFilterChain ->
            val request = exchange.request
            val response = exchange.response
            log.info("Global Filter baseMessage: {}", config?.baseMessage)
            if (config?.preLogger == true) {
                log.info("Global PRE filter request id - {}", request.id)
            }
            chain.filter(exchange)
                    .then(Mono.fromRunnable {
                        if (config?.postLogger == true) {
                            log.info("Global POST filter response code - {}", response.statusCode)
                        }
                    })
        }
    }
}