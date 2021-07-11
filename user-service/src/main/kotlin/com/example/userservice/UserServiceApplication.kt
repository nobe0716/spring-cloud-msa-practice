package com.example.userservice

import com.example.userservice.error.FeignErrorDecoder
import feign.Logger
import feign.codec.ErrorDecoder
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.client.RestTemplate

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
class UserServiceApplication {
    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }

    @Bean
    fun feignLoggerLevel(): Logger.Level {
        return Logger.Level.FULL;
    }

    @Bean
    fun feignErrorDecoder(): ErrorDecoder {
        return FeignErrorDecoder()
    }
}

fun main(args: Array<String>) {
    runApplication<UserServiceApplication>(*args)
}
