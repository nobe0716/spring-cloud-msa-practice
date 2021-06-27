package com.example.userservice.controller

import com.example.userservice.dto.UserDto
import com.example.userservice.service.UserService
import com.example.userservice.vo.Greeting
import com.example.userservice.vo.RequestUser
import com.example.userservice.vo.ResponseUser
import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/")
class UserController(val userServiceImpl: UserService, val greeting: Greeting, val environment: Environment) {
    @GetMapping("/welcome")
    fun welcome(): String {
        return greeting.message
    }

    @PostMapping("/users")
    fun createUser(@RequestBody user: RequestUser?): ResponseEntity<ResponseUser> {
        val mapper = ModelMapper()
        mapper.configuration.matchingStrategy = MatchingStrategies.STRICT
        var userDto: UserDto = mapper.map(user, UserDto::class.java)
        userDto = userServiceImpl.createUser(userDto)
        val responseUser: ResponseUser = mapper.map(userDto, ResponseUser::class.java)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(responseUser)
    }

    @ResponseBody
    @GetMapping("/users/{userId}")
    fun getUsers(@PathVariable userId: String): ResponseUser {
        val user = userServiceImpl.getUserById(userId)
        val mapper = ModelMapper()
        mapper.configuration.matchingStrategy = MatchingStrategies.STRICT
        return mapper.map(user, ResponseUser::class.java)
    }


    @GetMapping("/users")
    fun getUsers(): List<ResponseUser> {
        val userByAll = userServiceImpl.getUserByAll()
        val mapper = ModelMapper()
        mapper.configuration.matchingStrategy = MatchingStrategies.STRICT

        val responseUsers = userByAll.map { mapper.map(it, ResponseUser::class.java) }
            .toList()
        return responseUsers
    }

    @GetMapping("/health_check")
    fun status(): String {
        return "It's working in User Service on port ${environment.getProperty("server.port")}"
    }
}