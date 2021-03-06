package ru.otus.spring.kushchenko.hw6.controller

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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.otus.spring.kushchenko.hw6.dto.BookRequest
import ru.otus.spring.kushchenko.hw6.entity.Author
import ru.otus.spring.kushchenko.hw6.entity.Book
import ru.otus.spring.kushchenko.hw6.entity.Comment
import ru.otus.spring.kushchenko.hw6.entity.Genre
import ru.otus.spring.kushchenko.hw6.entity.User
import ru.otus.spring.kushchenko.hw6.service.BookService
import ru.otus.spring.kushchenko.hw6.util.Util.asJsonString
import java.time.LocalDateTime

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
        val user1 = User(1, "User1")
        val user2 = User(2, "User2")
        val comment1 = Comment(1, "Text", LocalDateTime.now(), user1, 1)
        val comment2 = Comment(2, "Text", LocalDateTime.now(), user2, 2)

        val books = listOf(
            Book(
                id = 1,
                name = "Book1",
                originalName = "Book1 Original Name",
                paperback = 111,
                authors = listOf(author1, author2),
                genres = listOf(genre1, genre2),
                user = user1,
                comments = listOf(comment1)
            ),
            Book(
                id = 2,
                name = "Book2",
                originalName = "Book2 Original Name",
                paperback = 222,
                authors = listOf(author2, author3),
                genres = listOf(genre2, genre3),
                user = user2,
                comments = listOf(comment2)
            )
        )

        whenever(service.getAll()).thenReturn(books)

        mockMvc.perform(get(BASE_URL))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().json(books.asJsonString()))
            .andReturn().response

        verify(service).getAll()
        verifyNoMoreInteractions(service)
    }

    @Test
    fun get() {
        val author = Author(1, "Author1")
        val genre = Genre(1, "Genre1")
        val user = User(1, "User1")
        val comment = Comment(1, "Text", LocalDateTime.now(), user, 1)

        val bookId = 1
        val book = Book(
            id = bookId,
            name = "Book1",
            originalName = "Book1 Original Name",
            paperback = 111,
            authors = listOf(author),
            genres = listOf(genre),
            user = user,
            comments = listOf(comment)
        )

        whenever(service.get(bookId)).thenReturn(book)

        mockMvc.perform(get("$BASE_URL/{id}", bookId))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().json(book.asJsonString()))
            .andReturn().response

        verify(service).get(bookId)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun create() {
        val author = Author(1, "Author1")
        val genre = Genre(1, "Genre1")

        val book = Book(
            id = 1,
            name = "Book1",
            originalName = "Book1 Original Name",
            paperback = 111,
            authors = listOf(author),
            genres = listOf(genre)
        )
        val bookRequest = book.toRequest()

        whenever(service.create(Book(bookRequest))).thenReturn(book)

        mockMvc.perform(
            post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookRequest.asJsonString())
        ).andDo(::print)
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().json(book.asJsonString()))
            .andReturn().response

        verify(service).create(Book(bookRequest))
        verifyNoMoreInteractions(service)
    }

    @Test
    fun update() {
        val author = Author(1, "Author1")
        val genre = Genre(1, "Genre1")

        val bookId = 1
        val book = Book(
            id = bookId,
            name = "Book1",
            originalName = "Book1 Original Name",
            paperback = 111,
            authors = listOf(author),
            genres = listOf(genre)
        )
        val bookRequest = book.toRequest()

        whenever(service.update(bookId, Book(bookRequest))).thenReturn(book)

        mockMvc.perform(
            put("$BASE_URL/{id}", bookId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookRequest.asJsonString())
        ).andDo(::print)
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().json(book.asJsonString()))
            .andReturn().response

        verify(service).update(bookId, Book(bookRequest))
        verifyNoMoreInteractions(service)
    }

    @Test
    fun delete() {
        val bookId = 1

        doNothing().whenever(service).delete(bookId)
        mockMvc.perform(
            delete("$BASE_URL/{id}", bookId)
        )
            .andExpect(status().isOk)

        verify(service).delete(bookId)
        verifyNoMoreInteractions(service)
    }

    private fun Book.toRequest() =
        BookRequest(
            name = this.name,
            originalName = this.originalName,
            paperback = this.paperback,
            authorIds = this.authors?.map { it.id!! },
            genreIds = this.genres?.map { it.id!! }
        )
}