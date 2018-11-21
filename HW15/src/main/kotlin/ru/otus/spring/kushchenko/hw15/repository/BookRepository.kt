package ru.otus.spring.kushchenko.hw15.repository

import org.springframework.data.mongodb.repository.MongoRepository
import ru.otus.spring.kushchenko.hw15.model.Book

interface BookRepository : MongoRepository<Book, String>