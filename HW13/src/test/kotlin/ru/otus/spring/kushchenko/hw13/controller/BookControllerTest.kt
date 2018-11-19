package ru.otus.spring.kushchenko.hw13.controller

import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.CoreMatchers.hasItem
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view
import ru.otus.spring.kushchenko.hw13.model.Book
import ru.otus.spring.kushchenko.hw13.model.ShortBook
import ru.otus.spring.kushchenko.hw13.service.BookService

/**
 * Created by Елена on Нояб., 2018
 */
@ExtendWith(SpringExtension::class)
@WebMvcTest(BookController::class)
class BookControllerTest {
    private val BASE_URL = "/books"

    @Autowired
    private lateinit var mockMvc: MockMvc
    @MockBean
    private lateinit var service: BookService

    @AfterEach
    fun clean() {
        reset(service)
    }

    @Test
    @WithMockUser
    fun getAll() {
        val book1 = ShortBook(
            id = "1",
            name = "Book1",
            authors = listOf("Author1", "Author2"),
            genres = listOf("Genre1", "Genre2")
        )
        val book2 = ShortBook(
            id = "2",
            name = "Book2",
            authors = listOf("Author2", "Author3"),
            genres = listOf("Genre2", "Genre3")
        )

        val books = listOf(book1, book2)

        whenever(service.getAll()).thenReturn(books)

        mockMvc.perform(get(BASE_URL))
            .andExpect(status().isOk)
            .andExpect(view().name("books"))
            .andExpect(model().attribute("books", hasItem(book1)))
            .andExpect(model().attribute("books", hasItem(book2)))
            .andReturn().response

        verify(service).getAll()
        verifyNoMoreInteractions(service)
    }

    @Test
    @WithMockUser
    fun get() {
        val bookId = "1"
        val book = Book(
            id = bookId,
            name = "Book1",
            originalName = "Book1 Original Name",
            paperback = 111,
            authors = listOf("Author1"),
            genres = listOf("Genre1")
        )

        whenever(service.get(bookId)).thenReturn(book)

        mockMvc.perform(get("$BASE_URL/{id}", bookId))
            .andExpect(status().isOk)
            .andExpect(view().name("book"))
            .andExpect(model().attribute("book", book))
            .andReturn().response

        verify(service).get(bookId)
        verifyNoMoreInteractions(service)
    }

    @Nested
    @DisplayName("Tests for create() method")
    inner class Create {

        @Test
        @WithMockUser(authorities = ["ADMIN"])
        fun shouldPassSuccessfully() {
            val book = Book(
                name = "Book1",
                originalName = "Book1 Original Name",
                paperback = 111,
                authors = listOf("Author1"),
                genres = listOf("Genre1")
            )

            whenever(service.create(book)).thenReturn(book.copy(id = "1"))

            mockMvc.perform(
                post(BASE_URL)
                    .param("name", book.name)
                    .param("originalName", book.originalName)
                    .param("paperback", book.paperback.toString())
                    .param("authors", book.authors!!.joinToString(","))
                    .param("genres", book.genres!!.joinToString(","))
            )
                .andExpect(status().isFound)
                .andExpect(MockMvcResultMatchers.redirectedUrl("/books"))
                .andReturn().response

            verify(service).create(book)
            verifyNoMoreInteractions(service)
        }

        @Test
        @WithMockUser(authorities = ["USER"])
        fun shouldFailBecauseNoPermissions() {
            val book = Book(
                name = "Book1",
                originalName = "Book1 Original Name",
                paperback = 111,
                authors = listOf("Author1"),
                genres = listOf("Genre1")
            )

            whenever(service.create(book)).thenThrow(AccessDeniedException(""))

            mockMvc.perform(
                post(BASE_URL)
                    .param("name", book.name)
                    .param("originalName", book.originalName)
                    .param("paperback", book.paperback.toString())
                    .param("authors", book.authors!!.joinToString(","))
                    .param("genres", book.genres!!.joinToString(","))
            )
                .andExpect(status().isInternalServerError)
                .andExpect(view().name("error"))
                .andReturn().response

            verify(service).create(book)
            verifyNoMoreInteractions(service)
        }
    }

    @Nested
    @DisplayName("Tests for delete() method")
    inner class Delete {

        @Test
        @WithMockUser(authorities = ["ADMIN"])
        fun shouldPassSuccessfully() {
            val bookId = "1"

            doNothing().whenever(service).delete(bookId)
            mockMvc.perform(
                get("$BASE_URL/{id}/delete", bookId)
            )
                .andExpect(status().isFound)
                .andExpect(MockMvcResultMatchers.redirectedUrl("/books"))
                .andReturn().response

            verify(service).delete(bookId)
            verifyNoMoreInteractions(service)
        }

        @Test
        @WithMockUser(authorities = ["USER"])
        fun shouldFailBecauseNoPermissions() {
            val bookId = "1"

            whenever(service.delete(bookId)).thenThrow(AccessDeniedException(""))

            mockMvc.perform(
                get("$BASE_URL/{id}/delete", bookId)
            )
                .andExpect(status().isInternalServerError)
                .andExpect(view().name("error"))
                .andReturn().response

            verify(service).delete(bookId)
            verifyNoMoreInteractions(service)
        }
    }
}