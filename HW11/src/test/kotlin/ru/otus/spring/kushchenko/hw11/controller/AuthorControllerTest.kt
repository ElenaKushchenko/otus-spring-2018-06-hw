package ru.otus.spring.kushchenko.hw11.controller

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import ru.otus.spring.kushchenko.hw11.model.Author
import ru.otus.spring.kushchenko.hw11.service.AuthorService
import java.time.LocalDate

/**
 * Created by Елена on Авг., 2018
 */
@ExtendWith(SpringExtension::class)
@WebFluxTest(AuthorController::class)
class AuthorControllerTest {
    private val BASE_URL = "/authors"

    @Autowired
    private lateinit var webClient: WebTestClient
    @MockBean
    private lateinit var service: AuthorService

    @Test
    fun getAll() {
        val authors = listOf(
            Author("Author1", "USA", LocalDate.parse("1901-01-01")),
            Author("Author2", "Russia", LocalDate.parse("1902-02-02"))
        )

        whenever(service.getAll()).thenReturn(Flux.fromIterable(authors))

        webClient.get().uri(BASE_URL)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(Author::class.java)
            .hasSize(authors.size)
            .contains(*authors.toTypedArray())

        verify(service).getAll()
        verifyNoMoreInteractions(service)
    }
}