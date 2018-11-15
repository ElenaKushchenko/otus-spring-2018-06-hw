package ru.otus.spring.kushchenko.hw11.repository

import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.toFlux

@Repository
class GenreRepositoryImpl(private val template: ReactiveMongoTemplate): GenreRepository {

    override fun getAll(): Flux<String> =
        template.getCollection("books")
            .distinct("genres", String::class.java)
            .toFlux()
}