package ru.otus.spring.kushchenko.hw5.controller

import com.jayway.jsonassert.impl.matcher.IsCollectionWithSize.hasSize
import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.CoreMatchers.`is`
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.otus.spring.kushchenko.hw5.model.Author
import ru.otus.spring.kushchenko.hw5.service.AuthorService
import ru.otus.spring.kushchenko.hw5.util.Util.asJsonString

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
        val expected = listOf(
            Author(1, "Author1"),
            Author(2, "Author2")
        )

        whenever(service.getAll()).thenReturn(expected)

        mockMvc.perform(get(BASE_URL))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray)
            .andExpect(jsonPath("$", hasSize(expected.size)))
            .andExpect(jsonPath("$[0].id").value(expected[0].id))
            .andExpect(jsonPath("$[0].name").value(expected[0].name))
            .andExpect(jsonPath("$[1].id").value(expected[1].id))
            .andExpect(jsonPath("$[1].name").value(expected[1].name))
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
            .andExpect(jsonPath("$.id", `is`(author.id)))
            .andExpect(jsonPath("$.name", `is`(author.name)))

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
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id", `is`(author.id)))
            .andExpect(jsonPath("$.name", `is`(author.name)))

        verify(service).create(author)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun update() {
        val authorId = 1
        val author = Author(authorId, "Author1")

        whenever(service.update(authorId, author)).thenReturn(author)

        mockMvc.perform(
            put("$BASE_URL/{id}", authorId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(author.asJsonString())
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id", `is`(author.id)))
            .andExpect(jsonPath("$.name", `is`(author.name)))

        verify(service).update(authorId, author)
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