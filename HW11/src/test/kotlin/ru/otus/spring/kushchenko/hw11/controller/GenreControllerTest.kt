package ru.otus.spring.kushchenko.hw11.controller

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import jdk.internal.org.objectweb.asm.TypeReference
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import ru.otus.spring.kushchenko.hw11.service.GenreService

/**
 * Created by Елена on Авг., 2018
 */
@ExtendWith(SpringExtension::class)
@WebFluxTest(GenreController::class)
class GenreControllerTest {
    private val BASE_URL = "/genres"

    @Autowired
    private lateinit var webClient: WebTestClient
    @MockBean
    private lateinit var service: GenreService

    @Test
    fun getAll() {
        val genres = listOf("Genre1", "Genre2")

        whenever(service.getAll()).thenReturn(Flux.fromIterable(genres))

        webClient.get().uri(BASE_URL)
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(String::class.java)
            .hasSize(genres.size)               //problem is reproduced only with strings
            .contains(*genres.toTypedArray())

        verify(service).getAll()
        verifyNoMoreInteractions(service)
    }
}