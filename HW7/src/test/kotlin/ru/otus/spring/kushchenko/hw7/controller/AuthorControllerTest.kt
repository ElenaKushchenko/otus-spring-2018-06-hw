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
import ru.otus.spring.kushchenko.hw7.service.AuthorService
import ru.otus.spring.kushchenko.hw7.util.Util.asJsonString

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
            Author(1, "Author1"),
            Author(2, "Author2")
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

    @Test
    fun get() {
        val authorId = 1
        val author = Author(authorId, "Author1")

        whenever(service.get(authorId)).thenReturn(author)

        mockMvc.perform(get("$BASE_URL/{id}", authorId))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().json(author.asJsonString()))
            .andReturn().response

        verify(service).get(authorId)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun create() {
        val author = Author(1, "Author1")

        whenever(service.create(author)).thenReturn(author)

        mockMvc.perform(
            post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(author.asJsonString())
        ).andDo(::print)
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().json(author.asJsonString()))
            .andReturn().response

        verify(service).create(author)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun update() {
        val authorId = 1
        val author = Author(authorId, "Author1")

        whenever(service.update(author)).thenReturn(author)

        mockMvc.perform(
            put("$BASE_URL/{id}", authorId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(author.asJsonString())
        ).andDo(::print)
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().json(author.asJsonString()))
            .andReturn().response

        verify(service).update(author)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun delete() {
        val authorId = 1

        doNothing().whenever(service).delete(authorId)
        mockMvc.perform(
            delete("$BASE_URL/{id}", authorId)
        )
            .andExpect(status().isOk)

        verify(service).delete(authorId)
        verifyNoMoreInteractions(service)
    }
}