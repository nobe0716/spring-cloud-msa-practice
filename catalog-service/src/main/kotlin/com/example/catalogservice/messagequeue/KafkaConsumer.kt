package com.example.catalogservice.messagequeue

import com.example.catalogservice.Log
import com.example.catalogservice.entity.CatalogRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaConsumer(val catalogRepository: CatalogRepository) {
    companion object : Log

    private val objectMapper = ObjectMapper()

    @KafkaListener(topics = ["example-catalog-topic"])
    fun updateQty(kafkaMessage: String) {
        log.info("kafka Message: {}", kafkaMessage)
//        Map<String, Object> map = new HashMap<>()
        val map = objectMapper.readValue(kafkaMessage, HashMap::class.java)

        val entity = catalogRepository.findByProductId(map["productId"] as String)
        entity.let {
            entity.stock = entity.stock!! - (map["qty"] as Int)
            log.info("update entity {}", entity)
            catalogRepository.save(entity)
        }
    }
}