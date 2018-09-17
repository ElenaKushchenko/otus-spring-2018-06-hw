package ru.otus.spring.kushchenko.hw8.repository

import org.springframework.data.mongodb.repository.MongoRepository
import ru.otus.spring.kushchenko.hw8.entity.Book

interface BookRepository : MongoRepository<Book, Int>