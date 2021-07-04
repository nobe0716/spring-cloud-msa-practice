package com.example.userservice.service

import com.example.userservice.dto.UserDto
import com.example.userservice.jpa.UserEntity
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService: UserDetailsService {
    fun createUser(userDto: UserDto): UserDto
    fun getUserById(userId: String): UserDto?
    fun getUserByAll(): MutableIterable<UserEntity>
    fun getUserDetailsByEmail(username: String): UserDto
}