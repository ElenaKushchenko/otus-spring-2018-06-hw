package ru.otus.spring.kushchenko.hw11.repository

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import ru.otus.spring.kushchenko.hw11.model.User

interface UserRepository : ReactiveMongoRepository<User, String>