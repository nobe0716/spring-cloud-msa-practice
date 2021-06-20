package com.example.userservice.jpa

import javax.persistence.*

@Entity
@Table(name = "users")
class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false, length = 50)
    var email: String? = null

    @Column(nullable = false, length = 50)
    var name: String? = null

    @Column(nullable = false, unique = true)
    var userId: String? = null

    @Column(nullable = false, unique = true)
    var encryptedPwd: String? = null
}