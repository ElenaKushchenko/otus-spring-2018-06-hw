package ru.otus.spring.kushchenko.hw11.repository

import com.mongodb.DBObject
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.toFlux
import ru.otus.spring.kushchenko.hw11.model.Author
import java.time.ZoneId
import java.util.*

@Repository
class AuthorRepositoryImpl(private val template: ReactiveMongoTemplate) : AuthorRepository {

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

    override fun getAll(): Flux<Author> =
        template.getCollection("books")
            .distinct("authors", DBObject::class.java)
            .toFlux()
            .map(authorMapper)
}