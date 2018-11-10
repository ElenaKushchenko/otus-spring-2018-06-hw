package ru.otus.spring.kushchenko.hw9.util

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import ru.otus.spring.kushchenko.hw9.model.Book
import ru.otus.spring.kushchenko.hw9.model.ShortBook

/**
 * Created by Елена on Авг., 2018
 */
object Util {
    private val objectMapper = Jackson2ObjectMapperBuilder().build<ObjectMapper>()

    fun Any?.asJsonString(): String {
        try {
            return objectMapper.writeValueAsString(this)
        } catch (ex: Exception) {
            throw RuntimeException(ex)
        }
    }

    fun Book.toShortBook(): ShortBook {
        val book = this
        return object : ShortBook {
            override fun getId() = book.id
            override fun getName() = book.name
            override fun getAuthors() = book.authors
            override fun getGenres() = book.genres
        }
    }
}