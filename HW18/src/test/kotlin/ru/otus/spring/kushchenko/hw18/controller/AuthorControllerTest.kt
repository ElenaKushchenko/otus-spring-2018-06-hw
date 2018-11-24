package ru.otus.spring.kushchenko.hw18.controller

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.otus.spring.kushchenko.hw18.model.Author
import ru.otus.spring.kushchenko.hw18.service.AuthorService
import ru.otus.spring.kushchenko.hw18.util.Util.asJsonString
import java.time.LocalDate

/**
 * Created by Елена on Авг., 2018
 */
@ExtendWith(SpringExtension::class)
@WebMvcTest(AuthorController::class)
class AuthorControllerTest {
    private val BASE_URL = "/authors"

    @Autowired
    private lateinit var mockMvc: MockMvc
    @MockBean
    private lateinit var service: AuthorService

    @Test
    fun getAll() {
        val authors = listOf(
            Author("Author1", "USA", LocalDate.parse("1901-01-01")),
            Author("Author2", "Russia", LocalDate.parse("1902-02-02"))
        )

        whenever(service.getAll()).thenReturn(authors)

        mockMvc.perform(get(BASE_URL))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().json(authors.asJsonString()))
            .andReturn().response

        verify(service).getAll()
        verifyNoMoreInteractions(service)
    }
}