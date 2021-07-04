package com.example.userservice.vo

import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class RequestLogin {
    val email: @NotNull(message = "email should not be null") @Size(min = 2) @Email String? = null
    val password: @NotNull @Size(min = 8) String? = null
}