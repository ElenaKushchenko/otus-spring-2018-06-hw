package ru.otus.spring.kushchenko.adminclient.repository

import org.springframework.data.mongodb.repository.MongoRepository
import ru.otus.spring.kushchenko.adminclient.model.Book

interface BookRepository : MongoRepository<Book, String>