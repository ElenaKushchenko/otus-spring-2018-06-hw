package ru.otus.spring.kushchenko.hw5.controller

import com.jayway.jsonassert.impl.matcher.IsCollectionWithSize
import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import ru.otus.spring.kushchenko.hw5.model.Author
import ru.otus.spring.kushchenko.hw5.model.Book
import ru.otus.spring.kushchenko.hw5.model.Genre
import ru.otus.spring.kushchenko.hw5.model.Reader
import ru.otus.spring.kushchenko.hw5.service.BookService
import ru.otus.spring.kushchenko.hw5.util.Util.asJsonString

/**
 * Created by Елена on Авг., 2018
 */
@ExtendWith(SpringExtension::class)
@WebMvcTest(BookController::class)
class BookControllerTest {
    private val BASE_URL = "/books"

    @Autowired
    private lateinit var mockMvc: MockMvc
    @MockBean
    private lateinit var service: BookService

    @Test
    fun getAll() {
        val author1 = Author(1, "Author1")
        val author2 = Author(2, "Author2")
        val author3 = Author(3, "Author3")
        val genre1 = Genre(1, "Genre1")
        val genre2 = Genre(2, "Genre2")
        val genre3 = Genre(3, "Genre3")
        val reader1 = Reader(1, "Reader1")
        val reader2 = Reader(2, "Reader2")

        val expected = listOf(
            Book(1, "Book1", "Book1 Original Name", 111, listOf(author1, author2), listOf(genre1, genre2), reader1),
            Book(2, "Book2", "Book2 Original Name", 222, listOf(author2, author3), listOf(genre2, genre3), reader2)
        )

        whenever(service.getAll()).thenReturn(expected)

        mockMvc.perform(get(BASE_URL))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$", IsCollectionWithSize.hasSize(expected.size)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(expected[0].id))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(expected[0].name))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].originalName").value(expected[0].originalName!!))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].paperback").value(expected[0].paperback!!))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].authors").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].authors", IsCollectionWithSize.hasSize(expected[0].authors!!.size)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].authors[0].id").value(author1.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].authors[0].name").value(author1.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].authors[1].id").value(author2.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].authors[1].name").value(author2.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].genres").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].genres", IsCollectionWithSize.hasSize(expected[0].genres!!.size)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].genres[0].id").value(genre1.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].genres[0].name").value(genre1.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].genres[1].id").value(genre2.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].genres[1].name").value(genre2.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].reader.id").value(reader1.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].reader.name").value(reader1.name))

            .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(expected[1].id))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(expected[1].name))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].originalName").value(expected[1].originalName!!))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].paperback").value(expected[1].paperback!!))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].authors").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].authors", IsCollectionWithSize.hasSize(expected[1].authors!!.size)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].authors[0].id").value(author2.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].authors[0].name").value(author2.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].authors[1].id").value(author3.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].authors[1].name").value(author3.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].genres").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].genres", IsCollectionWithSize.hasSize(expected[1].genres!!.size)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].genres[0].id").value(genre2.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].genres[0].name").value(genre2.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].genres[1].id").value(genre3.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].genres[1].name").value(genre3.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].reader.id").value(reader2.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].reader.name").value(reader2.name))
            .andReturn().response

        verify(service).getAll()
        verifyNoMoreInteractions(service)
    }

    @Test
    fun get() {
        val author = Author(1, "Author1")
        val genre = Genre(1, "Genre1")
        val reader = Reader(1, "Reader1")

        val bookId = 1
        val book = Book(bookId, "Book1", "Book1 Original Name", 111, listOf(author), listOf(genre), reader)

        whenever(service.get(bookId)).thenReturn(book)

        mockMvc.perform(get("$BASE_URL/{id}", bookId))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(book.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(book.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.originalName").value(book.originalName!!))
            .andExpect(MockMvcResultMatchers.jsonPath("$.paperback").value(book.paperback!!))
            .andExpect(MockMvcResultMatchers.jsonPath("$.authors").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$.authors", IsCollectionWithSize.hasSize(book.authors!!.size)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.authors[0].id").value(author.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.authors[0].name").value(author.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.genres").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$.genres", IsCollectionWithSize.hasSize(book.genres!!.size)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.genres[0].id").value(genre.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.genres[0].name").value(genre.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.reader.id").value(reader.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.reader.name").value(reader.name))

        verify(service).get(1)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun create() {
        val author = Author(1, "Author1")
        val genre = Genre(1, "Genre1")
        val reader = Reader(1, "Reader1")

        val book = Book(1, "Book1", "Book1 Original Name", 111, listOf(author), listOf(genre), reader)

        whenever(service.create(book)).thenReturn(book)

        mockMvc.perform(
            post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(book.asJsonString())
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(book.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(book.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.originalName").value(book.originalName!!))
            .andExpect(MockMvcResultMatchers.jsonPath("$.paperback").value(book.paperback!!))
            .andExpect(MockMvcResultMatchers.jsonPath("$.authors").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$.authors", IsCollectionWithSize.hasSize(book.authors!!.size)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.authors[0].id").value(author.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.authors[0].name").value(author.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.genres").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$.genres", IsCollectionWithSize.hasSize(book.genres!!.size)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.genres[0].id").value(genre.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.genres[0].name").value(genre.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.reader.id").value(reader.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.reader.name").value(reader.name))

        verify(service).create(book)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun update() {
        val author = Author(1, "Author1")
        val genre = Genre(1, "Genre1")
        val reader = Reader(1, "Reader1")

        val bookId = 1
        val book = Book(bookId, "Book1", "Book1 Original Name", 111, listOf(author), listOf(genre), reader)

        whenever(service.update(bookId, book)).thenReturn(book)

        mockMvc.perform(
            put("$BASE_URL/{id}", bookId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(book.asJsonString())
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(book.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(book.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.originalName").value(book.originalName!!))
            .andExpect(MockMvcResultMatchers.jsonPath("$.paperback").value(book.paperback!!))
            .andExpect(MockMvcResultMatchers.jsonPath("$.authors").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$.authors", IsCollectionWithSize.hasSize(book.authors!!.size)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.authors[0].id").value(author.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.authors[0].name").value(author.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.genres").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$.genres", IsCollectionWithSize.hasSize(book.genres!!.size)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.genres[0].id").value(genre.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.genres[0].name").value(genre.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.reader.id").value(reader.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.reader.name").value(reader.name))

        verify(service).update(bookId, book)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun delete() {
        val bookId = 1

        doNothing().whenever(service).delete(bookId)
        mockMvc.perform(
            delete("$BASE_URL/{id}", bookId)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)

        verify(service).delete(bookId)
        verifyNoMoreInteractions(service)
    }
}