package ru.otus.spring.kushchenko.hw8.repository

import org.springframework.data.mongodb.repository.MongoRepository
import ru.otus.spring.kushchenko.hw8.model.User

interface UserRepository : MongoRepository<User, String>