package com.example.userservice.controller

import com.example.userservice.dto.UserDto
import com.example.userservice.service.UserService
import com.example.userservice.vo.Greeting
import com.example.userservice.vo.RequestUser
import com.example.userservice.vo.ResponseUser
import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/")
class UserController(val userServiceImpl: UserService, val greeting: Greeting) {
    @GetMapping("/welcome")
    fun welcome(): String {
        return greeting.message
    }

    @PostMapping("/users")
    fun createUser(@RequestBody user: RequestUser?): ResponseEntity<ResponseUser> {
        val mapper = ModelMapper()
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT)
        var userDto: UserDto = mapper.map(user, UserDto::class.java)
        userDto = userServiceImpl.createUser(userDto)
        val responseUser: ResponseUser = mapper.map(userDto, ResponseUser::class.java)
        return ResponseEntity.status(HttpStatus.CREATED)
                .body<ResponseUser>(responseUser)
    }

    @GetMapping("/health_check")
    fun status(@Value("\${token.expiration_time}") expiration: String, @Value("\${token.secret}") secret: String): String {
        return """
            It's working in User Service
            Token info - expiration: $expiration and secret: $secret
        """.trimIndent()
    }
}