package ru.otus.spring.kushchenko.hw15.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import ru.otus.spring.kushchenko.hw15.model.Book
import ru.otus.spring.kushchenko.hw15.model.ShortBook

interface BookRepository : MongoRepository<Book, String> {
    @Query("{}")
    fun findAllShortBooks(): List<ShortBook>
    @Query("{}")
    fun findPagedShortBooks(pageable: Pageable): Page<ShortBook>
}