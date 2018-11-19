package ru.otus.spring.kushchenko.hw13.repository

import org.springframework.data.mongodb.repository.MongoRepository
import ru.otus.spring.kushchenko.hw13.model.User

interface UserRepository : MongoRepository<User, String> {
    fun findByUsername(username: String): User?
}