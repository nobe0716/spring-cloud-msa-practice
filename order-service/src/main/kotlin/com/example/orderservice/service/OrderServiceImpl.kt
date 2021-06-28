package com.example.orderservice.service

import com.example.orderservice.dto.OrderDto
import com.example.orderservice.entity.OrderEntity
import com.example.orderservice.repository.OrderRepository
import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import org.springframework.stereotype.Service
import java.util.*

@Service
class OrderServiceImpl(val orderRepository: OrderRepository) : OrderService {
    override fun createOrder(orderDto: OrderDto): OrderDto {
        orderDto.orderId = UUID.randomUUID().toString()
        orderDto.totalPrice = orderDto.qty * orderDto.unitPrice
        val modelMapper = ModelMapper()
        modelMapper.configuration.matchingStrategy = MatchingStrategies.STRICT

        var orderEntity: OrderEntity = modelMapper.map(orderDto, OrderEntity::class.java)
        orderEntity = orderRepository.save(orderEntity)
        return modelMapper.map(orderEntity, OrderDto::class.java)
    }

    override fun getOrderByOrderId(orderId: String): OrderDto {
        val orderEntity: OrderEntity = orderRepository.findByOrderId(orderId)
        val modelMapper = ModelMapper()
        modelMapper.configuration.matchingStrategy = MatchingStrategies.STRICT
        return modelMapper.map(orderEntity, OrderDto::class.java)
    }

    override fun getOrdersByUserId(userId: String): Iterable<OrderEntity> {
        return orderRepository.findByUserId(userId)
    }
}