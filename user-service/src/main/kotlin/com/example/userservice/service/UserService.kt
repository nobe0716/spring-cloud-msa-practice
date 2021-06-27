package com.example.userservice.service

import com.example.userservice.dto.UserDto
import com.example.userservice.jpa.UserEntity

interface UserService {
    fun createUser(userDto: UserDto): UserDto
    fun getUserById(userId: String): UserDto?
    fun getUserByAll(): MutableIterable<UserEntity?>
}