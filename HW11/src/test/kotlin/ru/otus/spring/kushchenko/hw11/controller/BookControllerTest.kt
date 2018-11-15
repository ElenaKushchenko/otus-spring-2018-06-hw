package ru.otus.spring.kushchenko.hw11.controller

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.otus.spring.kushchenko.hw11.model.Author
import ru.otus.spring.kushchenko.hw11.model.Book
import ru.otus.spring.kushchenko.hw11.service.BookService
import java.time.LocalDate

/**
 * Created by Елена on Авг., 2018
 */
@ExtendWith(SpringExtension::class)
@WebFluxTest(BookController::class)
class BookControllerTest {
    private val BASE_URL = "/books"

    @Autowired
    private lateinit var webClient: WebTestClient
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

        val books = listOf(
            Book(
                id = "1",
                name = "Book1",
                originalName = "Book1 Original Name",
                paperback = 111,
                authors = listOf(author1, author2),
                genres = listOf(genre1, genre2)
            ),
            Book(
                id = "2",
                name = "Book2",
                originalName = "Book2 Original Name",
                paperback = 222,
                authors = listOf(author2, author3),
                genres = listOf(genre2, genre3)
            )
        )

        whenever(service.getAll()).thenReturn(Flux.fromIterable(books))

        webClient.get().uri(BASE_URL)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(Book::class.java)
            .hasSize(books.size)
            .contains(*books.toTypedArray())

        verify(service).getAll()
        verifyNoMoreInteractions(service)
    }

    @Test
    fun getPaged() {
        val page = 1
        val size = 20
        val sortBy = "name"
        val dir = "ASC"

        val author1 = Author("Author1", "USA", LocalDate.parse("1900-03-01"))
        val author2 = Author("Author2", "Russia", LocalDate.parse("1900-02-01"))
        val author3 = Author("Author3", "UK", LocalDate.parse("1900-01-01"))
        val genre1 = "Genre1"
        val genre2 = "Genre2"
        val genre3 = "Genre3"

        val books = listOf(
            Book(
                id = "1",
                name = "Book1",
                originalName = "Book1 Original Name",
                paperback = 111,
                authors = listOf(author1, author2),
                genres = listOf(genre1, genre2)
            ),
            Book(
                id = "2",
                name = "Book2",
                originalName = "Book2 Original Name",
                paperback = 222,
                authors = listOf(author2, author3),
                genres = listOf(genre2, genre3)
            )
        )

        whenever(service.getPaged(page, size, sortBy, dir)).thenReturn(Flux.fromIterable(books))

        webClient.get().uri { builder ->
            builder.path("$BASE_URL/paged")
                .queryParam("page", page.toString())
                .queryParam("size", size.toString())
                .queryParam("sortBy", sortBy)
                .queryParam("dir", dir)
                .build()
        }
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(Book::class.java)
            .hasSize(books.size)
            .contains(*books.toTypedArray())

        verify(service).getPaged(page, size, sortBy, dir)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun get() {
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

        whenever(service.get(bookId)).thenReturn(Mono.just(book))

        webClient.get().uri("$BASE_URL/{id}", bookId)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus().isOk
            .expectBody(Book::class.java)
            .returnResult()
            .apply { assertEquals(book, responseBody) }

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

        whenever(service.create(book)).thenReturn(Mono.just(book))

        webClient.post().uri(BASE_URL)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .body(BodyInserters.fromObject(book))
            .exchange()
            .expectStatus().isOk
            .expectBody(Book::class.java)
            .returnResult()
            .apply { assertEquals(book, responseBody) }

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

        whenever(service.update(book)).thenReturn(Mono.just(book))

        webClient.put().uri("$BASE_URL/{id}", bookId)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .body(BodyInserters.fromObject(book))
            .exchange()
            .expectStatus().isOk
            .expectBody(Book::class.java)
            .returnResult()
            .apply { assertEquals(book, responseBody) }

        verify(service).update(book)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun delete() {
        val bookId = "1"

        whenever(service.delete(bookId)).thenReturn(Mono.empty<Void>())

        webClient.delete().uri("$BASE_URL/{id}", bookId)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus().isOk

        verify(service).delete(bookId)
        verifyNoMoreInteractions(service)
    }
}