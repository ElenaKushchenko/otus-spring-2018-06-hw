package ru.otus.spring.kushchenko.hw14.repository

import org.springframework.data.mongodb.repository.MongoRepository
import ru.otus.spring.kushchenko.hw14.model.mongo.Book

interface MongoBookRepository : MongoRepository<Book, String>