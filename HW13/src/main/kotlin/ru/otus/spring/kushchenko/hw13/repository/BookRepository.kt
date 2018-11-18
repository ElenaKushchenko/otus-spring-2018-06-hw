package ru.otus.spring.kushchenko.hw13.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import ru.otus.spring.kushchenko.hw13.model.Book
import ru.otus.spring.kushchenko.hw13.model.ShortBook

interface BookRepository : MongoRepository<Book, String> {
    @Query("{}")
    fun findAllShortBooks(): List<ShortBook>
}