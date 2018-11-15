package ru.otus.spring.kushchenko.hw12.repository

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository

@Repository
class AuthorRepositoryImpl(private val template: MongoTemplate): AuthorRepository {

    override fun getAll(): List<String> =
        template.getCollection("books")
            .distinct("authors", String::class.java)
            .toList()
}