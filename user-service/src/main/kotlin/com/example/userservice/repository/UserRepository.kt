package com.example.userservice.repository

import com.example.userservice.jpa.UserEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<UserEntity?, Long?>