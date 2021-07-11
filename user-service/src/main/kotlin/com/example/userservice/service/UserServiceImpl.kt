package com.example.userservice.service

import com.example.userservice.dto.UserDto
import com.example.userservice.exception.UserNotFoundException
import com.example.userservice.jpa.UserEntity
import com.example.userservice.repository.UserRepository
import com.example.userservice.vo.ResponseOrder
import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import org.springframework.core.ParameterizedTypeReference
import org.springframework.core.env.Environment
import org.springframework.http.HttpMethod
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.*

@Service
class UserServiceImpl(
    val userRepository: UserRepository,
    val bCryptPasswordEncoder: BCryptPasswordEncoder,
    val restTemplate: RestTemplate,
    val env: Environment
) : UserService {
    override fun createUser(userDto: UserDto): UserDto {
        userDto.userId = UUID.randomUUID().toString()
        val modelMapper = ModelMapper()
        modelMapper.configuration.matchingStrategy = MatchingStrategies.STRICT
        var userEntity: UserEntity = modelMapper.map(userDto, UserEntity::class.java)
        userEntity.encryptedPwd = bCryptPasswordEncoder.encode(userDto.pwd)
        userEntity = userRepository.save<UserEntity>(userEntity)
        return modelMapper.map(userEntity, UserDto::class.java)
    }

    override fun getUserByAll(): MutableIterable<UserEntity> {
        return userRepository.findAll()
    }

    override fun getUserById(userId: String): UserDto? {
        val modelMapper = ModelMapper()
        modelMapper.configuration.matchingStrategy = MatchingStrategies.STRICT
        val userDto = userRepository.findByUserId(userId)
            .map { modelMapper.map(it, UserDto::class.java) }
            .orElseThrow { UserNotFoundException(userId) }

        val orderUrl = "${env.getProperty("order_service.url")}/${userId}/orders"
        val entity = restTemplate.exchange(orderUrl, HttpMethod.GET, null, object : ParameterizedTypeReference<List<ResponseOrder>>() {})
        val orders = entity.body
        userDto.orders = orders
        return userDto
    }

    override fun getUserDetailsByEmail(username: String): UserDto {
        val userEntity: UserEntity = userRepository.findByEmail(username)
            .orElseThrow { UsernameNotFoundException(username) }
        return ModelMapper().map(userEntity, UserDto::class.java)
    }


    override fun loadUserByUsername(username: String): UserDetails {
        val userEntity: UserEntity = userRepository.findByEmail(username)
            .orElseThrow { UsernameNotFoundException(username) }
        return User(userEntity.email, userEntity.encryptedPwd, ArrayList())
    }
}