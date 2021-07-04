package com.example.apigatewayservice.filter

import com.example.apigatewayservice.Log
import io.jsonwebtoken.Jwts
import org.apache.commons.codec.binary.Base64
import org.apache.http.HttpHeaders
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class AuthorizationHeaderFilter(val env: Environment) : AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config>(Config::class.java) {
    companion object : Log

    class Config()

    override fun apply(config: Config?): GatewayFilter {
        return GatewayFilter { exchange: ServerWebExchange, chain: GatewayFilterChain ->
            val request = exchange.request
            if (!request.headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED)
            }

            val authorizationHeader = request.headers.getFirst(HttpHeaders.AUTHORIZATION)
            val jwt = authorizationHeader?.replace("Bearer", "")

            if(!isJwtValid(jwt)) {
                onError(exchange, "JWT token is not valid", HttpStatus.UNAUTHORIZED)
            }
            val mutableList = request.headers["token"]
            chain.filter(exchange)
        }
    }

    private fun onError(exchange: ServerWebExchange, message: String, errorStatusCode: HttpStatus): Mono<Void> {
        val response: ServerHttpResponse = exchange.response
        response.setStatusCode(errorStatusCode)
        log.error("{}", message)
        return response.setComplete()
    }

    private fun isJwtValid(jwt: String?): Boolean {
        try {
            val subject = Jwts.parser().setSigningKey(env.getProperty("token.secret")!!.toByteArray())
                .parseClaimsJws(jwt)
                .body
                .subject
            return subject.isNotEmpty()
        } catch(e: Exception) {
            log.error("", e)
            return false
        }
    }
}