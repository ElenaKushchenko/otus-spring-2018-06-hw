package ru.otus.spring.kushchenko.hw11.repository

import reactor.core.publisher.Flux

interface GenreRepository {
    fun getAll(): Flux<String>
}