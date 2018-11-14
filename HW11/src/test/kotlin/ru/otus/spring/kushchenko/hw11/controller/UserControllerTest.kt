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
import ru.otus.spring.kushchenko.hw11.model.User
import ru.otus.spring.kushchenko.hw11.service.UserService

/**
 * Created by Елена on Авг., 2018
 */
@ExtendWith(SpringExtension::class)
@WebFluxTest(UserController::class)
class UserControllerTest {
    private val BASE_URL = "/users"

    @Autowired
    private lateinit var webClient: WebTestClient
    @MockBean
    private lateinit var service: UserService

    @Test
    fun getAll() {
        val users = listOf(
            User("1", "User1"),
            User("2", "User2")
        )

        whenever(service.getAll()).thenReturn(Flux.fromIterable(users))

        webClient.get().uri(BASE_URL)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(User::class.java)
            .hasSize(users.size)
            .contains(*users.toTypedArray())

        verify(service).getAll()
        verifyNoMoreInteractions(service)
    }

    @Test
    fun get() {
        val userId = "1"
        val user = User(userId, "User1")

        whenever(service.get(userId)).thenReturn(Mono.just(user))

        webClient.get().uri("$BASE_URL/{id}", userId)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus().isOk
            .expectBody(User::class.java)
            .returnResult()
            .apply { Assertions.assertEquals(user, responseBody) }

        verify(service).get(userId)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun create() {
        val user = User("1", "User1")

        whenever(service.create(user)).thenReturn(Mono.just(user))

        webClient.post().uri(BASE_URL)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .body(BodyInserters.fromObject(user))
            .exchange()
            .expectStatus().isOk
            .expectBody(User::class.java)
            .returnResult()
            .apply { Assertions.assertEquals(user, responseBody) }

        verify(service).create(user)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun update() {
        val userId = "1"
        val user = User(userId, "User1")

        whenever(service.update(user)).thenReturn(Mono.just(user))

        webClient.put().uri("$BASE_URL/{id}", userId)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .body(BodyInserters.fromObject(user))
            .exchange()
            .expectStatus().isOk
            .expectBody(User::class.java)
            .returnResult()
            .apply { Assertions.assertEquals(user, responseBody) }

        verify(service).update(user)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun delete() {
        val userId = "1"

        whenever(service.delete(userId)).thenReturn(Mono.empty<Void>())

        webClient.delete().uri("$BASE_URL/{id}", userId)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus().isOk

        verify(service).delete(userId)
        verifyNoMoreInteractions(service)
    }
}