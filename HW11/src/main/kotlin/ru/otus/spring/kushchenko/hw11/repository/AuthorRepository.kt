package ru.otus.spring.kushchenko.hw11.repository

import reactor.core.publisher.Flux
import ru.otus.spring.kushchenko.hw11.model.Author

interface AuthorRepository {
    fun getAll(): Flux<Author>
}