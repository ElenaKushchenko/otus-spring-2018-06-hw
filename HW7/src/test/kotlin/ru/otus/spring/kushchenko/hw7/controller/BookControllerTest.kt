package ru.otus.spring.kushchenko.hw7.controller

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
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.otus.spring.kushchenko.hw7.model.Author
import ru.otus.spring.kushchenko.hw7.model.Book
import ru.otus.spring.kushchenko.hw7.model.Comment
import ru.otus.spring.kushchenko.hw7.model.Genre
import ru.otus.spring.kushchenko.hw7.model.ShortBook
import ru.otus.spring.kushchenko.hw7.model.User
import ru.otus.spring.kushchenko.hw7.service.BookService
import ru.otus.spring.kushchenko.hw7.util.Util.asJsonString
import ru.otus.spring.kushchenko.hw7.util.Util.toShortBook
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

        val books = listOf(
            Book(
                id = 1,
                name = "Book1",
                authors = listOf(author1, author2),
                genres = listOf(genre1, genre2)
            ).toShortBook(),
            Book(
                id = 2,
                name = "Book2",
                authors = listOf(author2, author3),
                genres = listOf(genre2, genre3)
            ).toShortBook()
        )

        whenever(service.getAllShortBooks()).thenReturn(books)

        mockMvc.perform(get("$BASE_URL/all"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().json(books.asJsonString()))
            .andReturn().response

        verify(service).getAllShortBooks()
        verifyNoMoreInteractions(service)
    }

    @Test
    fun find() {
        val author1 = Author(1, "Author1")
        val author2 = Author(2, "Author2")
        val author3 = Author(3, "Author3")
        val genre1 = Genre(1, "Genre1")
        val genre2 = Genre(2, "Genre2")
        val genre3 = Genre(3, "Genre3")

        val books: List<ShortBook> = listOf(
            Book(
                id = 1,
                name = "Book1",
                authors = listOf(author1, author2),
                genres = listOf(genre1, genre2)
            ).toShortBook(),
            Book(
                id = 2,
                name = "Book1",
                authors = listOf(author1, author3),
                genres = listOf(genre1, genre3)
            ).toShortBook()
        )

        val bookNameFilter = "Book1"
        val authorIdFilter = 1
        val genreIdFilter = 1
        val page = 1
        val size = 10

        val content = PageImpl(books, PageRequest.of(page - 1, size), books.size.toLong())

        whenever(service.find(bookNameFilter, authorIdFilter, genreIdFilter, page, size))
            .thenReturn(content)

        mockMvc.perform(get(BASE_URL)
            .param("name", bookNameFilter)
            .param("authorId", authorIdFilter.toString())
            .param("genreId", genreIdFilter.toString())
            .param("page", page.toString())
            .param("size", size.toString())
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().json(content.asJsonString()))
            .andReturn().response

        verify(service).find(bookNameFilter, authorIdFilter, genreIdFilter, page, size)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun get() {
        val author = Author(1, "Author1")
        val genre = Genre(1, "Genre1")
        val user = User(1, "User1")
        val comment = Comment(1, "Text", LocalDateTime.now(), user)

        val bookId = 1
        val book = Book(
            id = bookId,
            name = "Book1",
            originalName = "Book1 Original Name",
            paperback = 111,
            authors = listOf(author),
            genres = listOf(genre),
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
        val bookId = 1

        doNothing().whenever(service).delete(bookId)
        mockMvc.perform(
            delete("$BASE_URL/{id}", bookId)
        )
            .andExpect(status().isOk)

        verify(service).delete(bookId)
        verifyNoMoreInteractions(service)
    }
}