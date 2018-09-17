package ru.otus.spring.kushchenko.hw8.repository

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import ru.otus.spring.kushchenko.hw8.entity.Author
import org.springframework.data.mongodb.core.query.BasicQuery
import org.springframework.data.mongodb.core.query.Criteria


@Repository
class AuthorRepositoryImpl(private val template: MongoTemplate): AuthorRepository {

    override fun getAll(): List<Author> {
        val query = Query().fields().include("authors").exclude("id").fieldsObject

        return template.find(Query(Criteria.byExample(query)), Author::class.java)
    }
}