package com.example.catalogservice.controller

import com.example.catalogservice.Log
import com.example.catalogservice.service.CatalogService
import com.example.catalogservice.vo.ResponseCatalog
import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CatalogController(val environment: Environment, val catalogService: CatalogService) {
    companion object : Log

    @GetMapping("/catalogs")
    fun getUsers(): List<ResponseCatalog> {
        val catalogByAll = catalogService.getAllCatalogs()
        val mapper = ModelMapper()
        mapper.configuration.matchingStrategy = MatchingStrategies.STRICT

        return catalogByAll.map { mapper.map(it, ResponseCatalog::class.java) }
            .toList()
    }

    @GetMapping("/health_check")
    fun status(): String {
        return "It's working in Catalog Service on port ${environment.getProperty("server.port")}"
    }
}