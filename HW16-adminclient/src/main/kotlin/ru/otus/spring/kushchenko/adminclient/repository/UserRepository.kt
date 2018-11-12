package ru.otus.spring.kushchenko.adminclient.repository

import org.springframework.data.mongodb.repository.MongoRepository
import ru.otus.spring.kushchenko.adminclient.model.User

interface UserRepository : MongoRepository<User, String>