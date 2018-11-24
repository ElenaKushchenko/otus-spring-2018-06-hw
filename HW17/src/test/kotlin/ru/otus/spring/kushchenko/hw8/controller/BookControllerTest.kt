package ru.otus.spring.kushchenko.hw8.controller

import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.otus.spring.kushchenko.hw8.model.Author
import ru.otus.spring.kushchenko.hw8.model.Book
import ru.otus.spring.kushchenko.hw8.model.User
import ru.otus.spring.kushchenko.hw8.service.BookService
import ru.otus.spring.kushchenko.hw8.util.Util.asJsonString
import java.time.LocalDate

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
        val author1 = Author("Author1", "USA", LocalDate.parse("1900-03-01"))
        val author2 = Author("Author2", "Russia", LocalDate.parse("1900-02-01"))
        val author3 = Author("Author3", "UK", LocalDate.parse("1900-01-01"))
        val genre1 = "Genre1"
        val genre2 = "Genre2"
        val genre3 = "Genre3"
        val user1 = User("1", "User1")
        val user2 = User("2", "User2")

        val books = listOf(
            Book(
                id = "1",
                name = "Book1",
                originalName = "Book1 Original Name",
                paperback = 111,
                authors = listOf(author1, author2),
                genres = listOf(genre1, genre2),
                user = user1
            ),
            Book(
                id = "2",
                name = "Book2",
                originalName = "Book2 Original Name",
                paperback = 222,
                authors = listOf(author2, author3),
                genres = listOf(genre2, genre3),
                user = user2
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
    fun getPaged() {
        val page = 1
        val size = 20
        val sortBy = "name"
        val dir = "ASC"

        val pageable = PageRequest.of(
            page - 1,
            size,
            Sort(Sort.Direction.valueOf(dir), sortBy)
        )

        val author1 = Author("Author1", "USA", LocalDate.parse("1900-03-01"))
        val author2 = Author("Author2", "Russia", LocalDate.parse("1900-02-01"))
        val author3 = Author("Author3", "UK", LocalDate.parse("1900-01-01"))
        val genre1 = "Genre1"
        val genre2 = "Genre2"
        val genre3 = "Genre3"
        val user1 = User("1", "User1")
        val user2 = User("2", "User2")

        val books = listOf(
            Book(
                id = "1",
                name = "Book1",
                originalName = "Book1 Original Name",
                paperback = 111,
                authors = listOf(author1, author2),
                genres = listOf(genre1, genre2),
                user = user1
            ),
            Book(
                id = "2",
                name = "Book2",
                originalName = "Book2 Original Name",
                paperback = 222,
                authors = listOf(author2, author3),
                genres = listOf(genre2, genre3),
                user = user2
            )
        )
        val booksPage = PageImpl(books, pageable, books.size.toLong())

        whenever(service.getPaged(page, size, sortBy, dir)).thenReturn(booksPage)

        mockMvc.perform(
            get("$BASE_URL/paged")
                .param("page", page.toString())
                .param("size", size.toString())
                .param("sortBy", sortBy)
                .param("dir", dir)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().json(booksPage.asJsonString()))
            .andReturn().response

        verify(service).getPaged(page, size, sortBy, dir)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun get() {
        val author = Author("Author1", "USA", LocalDate.parse("1900-03-01"))
        val genre = "Genre1"
        val user = User("1", "User1")

        val bookId = "1"
        val book = Book(
            id = bookId,
            name = "Book1",
            originalName = "Book1 Original Name",
            paperback = 111,
            authors = listOf(author),
            genres = listOf(genre),
            user = user
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
        val author = Author("Author1", "USA", LocalDate.parse("1900-03-01"))
        val genre = "Genre1"

        val book = Book(
            id = "1",
            name = "Book1",
            originalName = "Book1 Original Name",
            paperback = 111,
            authors = listOf(author),
            genres = listOf(genre)
        )

        whenever(service.create(book)).thenReturn(book)

        mockMvc.perform(
            post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(book.asJsonString())
        ).andDo(::print)
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().json(book.asJsonString()))
            .andReturn().response

        verify(service).create(book)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun update() {
        val author = Author("Author1", "USA", LocalDate.parse("1900-03-01"))
        val genre = "Genre1"

        val bookId = "1"
        val book = Book(
            id = bookId,
            name = "Book1",
            originalName = "Book1 Original Name",
            paperback = 111,
            authors = listOf(author),
            genres = listOf(genre)
        )

        whenever(service.update(book)).thenReturn(book)

        mockMvc.perform(
            put("$BASE_URL/{id}", bookId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(book.asJsonString())
        ).andDo(::print)
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().json(book.asJsonString()))
            .andReturn().response

        verify(service).update(book)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun delete() {
        val bookId = "1"

        doNothing().whenever(service).delete(bookId)
        mockMvc.perform(
            delete("$BASE_URL/{id}", bookId)
        )
            .andExpect(status().isOk)

        verify(service).delete(bookId)
        verifyNoMoreInteractions(service)
    }
}