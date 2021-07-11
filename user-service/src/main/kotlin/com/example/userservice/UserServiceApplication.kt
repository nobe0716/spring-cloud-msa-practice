package com.example.userservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.client.RestTemplate

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
}

fun main(args: Array<String>) {
    runApplication<UserServiceApplication>(*args)
}
