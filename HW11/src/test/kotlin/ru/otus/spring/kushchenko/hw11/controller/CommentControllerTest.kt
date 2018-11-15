package ru.otus.spring.kushchenko.hw11.controller

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Assertions
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
import ru.otus.spring.kushchenko.hw11.model.Book
import ru.otus.spring.kushchenko.hw11.model.Comment
import ru.otus.spring.kushchenko.hw11.model.User
import ru.otus.spring.kushchenko.hw11.service.CommentService
import java.time.LocalDateTime

/**
 * Created by Елена on Авг., 2018
 */
@ExtendWith(SpringExtension::class)
@WebFluxTest(CommentController::class)
class CommentControllerTest {
    private val BASE_URL = "/comments"

    @Autowired
    private lateinit var webClient: WebTestClient
    @MockBean
    private lateinit var service: CommentService

    @Test
    fun getAll() {
        val user1 = User("1", "User1")
        val user2 = User("2", "User2")
        val book1 = Book("1", "Book1")
        val book2 = Book("2", "Book2")

        val comments = listOf(
            Comment("1", "Comment1", LocalDateTime.now().withNano(0), user1, book1),
            Comment("2", "Comment2", LocalDateTime.now().withNano(0), user2, book2)
        )

        whenever(service.getAll()).thenReturn(Flux.fromIterable(comments))

        webClient.get().uri(BASE_URL)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(Comment::class.java)
            .hasSize(comments.size)
            .contains(*comments.toTypedArray())

        verify(service).getAll()
        verifyNoMoreInteractions(service)
    }

    @Test
    fun getPaged() {
        val page = 1
        val size = 20
        val sortBy = "name"
        val dir = "ASC"

        val userId = "1"
        val bookId = "1"
        val user = User(userId, "User1")
        val book = Book(bookId, "Book1")

        val comments = listOf(
            Comment("1", "Comment1", LocalDateTime.now().withNano(0), user, book),
            Comment("2", "Comment2", LocalDateTime.now().withNano(0), user, book)
        )

        whenever(service.getFiltered(userId, bookId, page, size, sortBy, dir)).thenReturn(Flux.fromIterable(comments))

        webClient.get().uri { builder ->
            builder.path("$BASE_URL/paged")
                .queryParam("userId", userId)
                .queryParam("bookId", bookId)
                .queryParam("page", page.toString())
                .queryParam("size", size.toString())
                .queryParam("sortBy", sortBy)
                .queryParam("dir", dir)
                .build()
        }
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(Comment::class.java)
            .hasSize(comments.size)
            .contains(*comments.toTypedArray())

        verify(service).getFiltered(userId, bookId, page, size, sortBy, dir)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun get() {
        val user = User("1", "User1")
        val book = Book("1", "Book1")

        val commentId = "1"
        val comment = Comment(commentId, "Comment1", LocalDateTime.now().withNano(0), user, book)

        whenever(service.get(commentId)).thenReturn(Mono.just(comment))

        webClient.get().uri("$BASE_URL/{id}", commentId)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus().isOk
            .expectBody(Comment::class.java)
            .returnResult()
            .apply { Assertions.assertEquals(comment, responseBody) }

        verify(service).get(commentId)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun create() {
        val user = User("1", "User1")
        val book = Book("1", "Book1")

        val comment = Comment("1", "Comment1", LocalDateTime.now().withNano(0), user, book)

        whenever(service.create(comment)).thenReturn(Mono.just(comment))

        webClient.post().uri(BASE_URL)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .body(BodyInserters.fromObject(comment))
            .exchange()
            .expectStatus().isOk
            .expectBody(Comment::class.java)
            .returnResult()
            .apply { Assertions.assertEquals(comment, responseBody) }

        verify(service).create(comment)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun update() {
        val user = User("1", "User1")
        val book = Book("1", "Book1")

        val commentId = "1"
        val comment = Comment(commentId, "Comment1", LocalDateTime.now().withNano(0), user, book)

        whenever(service.update(comment)).thenReturn(Mono.just(comment))

        webClient.put().uri("$BASE_URL/{id}", commentId)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .body(BodyInserters.fromObject(comment))
            .exchange()
            .expectStatus().isOk
            .expectBody(Comment::class.java)
            .returnResult()
            .apply { Assertions.assertEquals(comment, responseBody) }

        verify(service).update(comment)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun delete() {
        val commentId = "1"

        whenever(service.delete(commentId)).thenReturn(Mono.empty<Void>())

        webClient.delete().uri("$BASE_URL/{id}", commentId)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus().isOk

        verify(service).delete(commentId)
        verifyNoMoreInteractions(service)
    }
}