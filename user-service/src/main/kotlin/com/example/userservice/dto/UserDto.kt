package com.example.userservice.dto

import java.util.*

data class UserDto(
        var email: String? = null,
        var name: String? = null,
        var pwd: String? = null,
        var userId: String? = null,
        var createdAt: Date? = null,
        var encryptedPwd: String? = null)

