package ru.otus.spring.kushchenko.hw11.repository

import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import ru.otus.spring.kushchenko.hw11.model.Book

interface BookRepository : ReactiveMongoRepository<Book, String> {
    @Query("{ id: {\$exists: true} }")
    fun getPaged(page: Pageable): Flux<Book>
}