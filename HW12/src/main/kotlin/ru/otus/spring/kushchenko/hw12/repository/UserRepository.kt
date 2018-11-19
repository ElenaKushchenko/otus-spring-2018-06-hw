package ru.otus.spring.kushchenko.hw12.repository

import org.springframework.data.mongodb.repository.MongoRepository
import ru.otus.spring.kushchenko.hw12.model.User

interface UserRepository : MongoRepository<User, String> {
    fun findByUsername(username: String): User?
}