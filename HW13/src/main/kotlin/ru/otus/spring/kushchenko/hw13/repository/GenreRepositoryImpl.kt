package ru.otus.spring.kushchenko.hw13.repository

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository

@Repository
class GenreRepositoryImpl(private val template: MongoTemplate): GenreRepository {

    override fun getAll(): List<String> =
        template.getCollection("books")
            .distinct("genres", String::class.java)
            .toList()
}