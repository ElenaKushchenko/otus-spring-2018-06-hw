package ru.otus.spring.kushchenko.hw8.repository

import com.mongodb.DBObject
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository
import ru.otus.spring.kushchenko.hw8.entity.Author

@Repository
class AuthorRepositoryImpl(private val template: MongoTemplate): AuthorRepository {

    override fun getAll(): List<Author> =
        template.getCollection("books")
            .distinct("authors", DBObject::class.java)
            .toList()
            .map {
                Author(
                    firstName = it.get("firstName") as String,
                    secondName = it.get("secondName") as String
                )
            }
}