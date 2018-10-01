package ru.otus.spring.kushchenko.hw8.repository

import com.mongodb.DBObject
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository
import ru.otus.spring.kushchenko.hw8.model.Author
import java.time.ZoneId
import java.util.Date

@Repository
class AuthorRepositoryImpl(private val template: MongoTemplate): AuthorRepository {

    private val authorMapper: ((DBObject) -> Author) = {
        val name = it.get("name") as String
        val country = it.get("country")?.let { it as String }
        val dayOfBirth = it.get("dayOfBirth")?.let {
            it as Date
            it.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        }

        Author(
            name = name,
            country = country,
            dayOfBirth = dayOfBirth
        )
    }

    override fun getAll(): List<Author> =
        template.getCollection("books")
            .distinct("authors", DBObject::class.java)
            .toList()
            .map(authorMapper)
}