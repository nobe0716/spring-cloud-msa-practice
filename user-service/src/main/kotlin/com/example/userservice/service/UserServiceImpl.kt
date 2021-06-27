package com.example.userservice.service

import com.example.userservice.dto.UserDto
import com.example.userservice.exception.UserNotFoundException
import com.example.userservice.jpa.UserEntity
import com.example.userservice.repository.UserRepository
import com.example.userservice.vo.ResponseOrder
import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl(val userRepository: UserRepository, val bCryptPasswordEncoder: BCryptPasswordEncoder) : UserService {
    override fun createUser(userDto: UserDto): UserDto {
        userDto.userId = UUID.randomUUID().toString()
        val modelMapper = ModelMapper()
        modelMapper.configuration.matchingStrategy = MatchingStrategies.STRICT
        var userEntity: UserEntity = modelMapper.map(userDto, UserEntity::class.java)
        userEntity.encryptedPwd = bCryptPasswordEncoder.encode(userDto.pwd)
        userEntity = userRepository.save<UserEntity>(userEntity)
        return modelMapper.map(userEntity, UserDto::class.java)
    }

    override fun getUserByAll(): MutableIterable<UserEntity?> {
        return userRepository.findAll()
    }

    override fun getUserById(userId: String): UserDto? {
        val modelMapper = ModelMapper()
        modelMapper.configuration.matchingStrategy = MatchingStrategies.STRICT
        val userDto = userRepository.findByUserId(userId)
            .map { modelMapper.map(it, UserDto::class.java) }
            .orElseThrow { UserNotFoundException(userId) }
        var orders = ArrayList<ResponseOrder>()
        userDto.orders = orders
        return userDto
    }
}