package com.example.catalogservice.service

import com.example.catalogservice.Log
import com.example.catalogservice.entity.CatalogEntity
import com.example.catalogservice.entity.CatalogRepository
import org.springframework.stereotype.Service

@Service
class CatalogServiceImpl(val catalogRepository: CatalogRepository): CatalogService {
    companion object : Log

    override fun getAllCatalogs(): Iterable<CatalogEntity> {
        return catalogRepository.findAll()
    }
}