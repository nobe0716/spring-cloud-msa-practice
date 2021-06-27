package com.example.catalogservice.entity

import org.hibernate.annotations.ColumnDefault
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "catalog")
class CatalogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false, length = 120, unique = true)
    var productId: String? = null

    @Column(nullable = false)
    var productName: String? = null

    @Column(nullable = false)
    var stock: Int? = null

    @Column(nullable = false)
    var unitPrice: Int? = null

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    var createdAt: Date? = null
}