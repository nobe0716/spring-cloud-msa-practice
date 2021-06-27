package com.example.userservice.exception

import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException

class UserNotFoundException(userId: String) : HttpClientErrorException(HttpStatus.NOT_FOUND, "$userId user not found!")