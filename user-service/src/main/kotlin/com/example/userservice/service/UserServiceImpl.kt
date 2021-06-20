package com.example.userservice.service

import com.example.userservice.dto.UserDto
import com.example.userservice.jpa.UserEntity
import com.example.userservice.repository.UserRepository
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
}