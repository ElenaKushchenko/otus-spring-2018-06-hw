package ru.otus.spring.kushchenko.hw5.controller

import com.jayway.jsonassert.impl.matcher.IsCollectionWithSize.hasSize
import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.CoreMatchers
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import ru.otus.spring.kushchenko.hw5.model.Genre
import ru.otus.spring.kushchenko.hw5.service.GenreService
import ru.otus.spring.kushchenko.hw5.util.Util.asJsonString

/**
 * Created by Елена on Авг., 2018
 */
@ExtendWith(SpringExtension::class)
@WebMvcTest(GenreController::class)
class GenreControllerTest {
    private val BASE_URL = "/genres"

    @Autowired
    private lateinit var mockMvc: MockMvc
    @MockBean
    private lateinit var service: GenreService

    @Test
    fun getAll() {
        val expected = listOf(
            Genre(1, "Genre1"),
            Genre(2, "Genre2")
        )

        whenever(service.getAll()).thenReturn(expected)

        mockMvc.perform(get(BASE_URL))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(expected.size)))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(expected[0].id))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(expected[0].name))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(expected[1].id))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(expected[1].name))
            .andReturn().response

        verify(service).getAll()
        verifyNoMoreInteractions(service)
    }

    @Test
    fun get() {
        val genreId = 1
        val genre = Genre(genreId, "Genre1")

        whenever(service.get(genreId)).thenReturn(genre)

        mockMvc.perform(get("$BASE_URL/{id}", genreId))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.`is`(genre.id)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.`is`(genre.name)))

        verify(service).get(genreId)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun create() {
        val genre = Genre(1, "Genre1")

        whenever(service.create(genre)).thenReturn(genre)

        mockMvc.perform(
            post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(genre.asJsonString())
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.`is`(genre.id)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.`is`(genre.name)))

        verify(service).create(genre)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun update() {
        val genreId = 1
        val genre = Genre(genreId, "Genre1")

        whenever(service.update(genreId, genre)).thenReturn(genre)

        mockMvc.perform(
            put("$BASE_URL/{id}", genreId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(genre.asJsonString())
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.`is`(genre.id)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.`is`(genre.name)))

        verify(service).update(genreId, genre)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun delete() {
        val genreId = 1

        doNothing().whenever(service).delete(genreId)
        mockMvc.perform(
            delete("$BASE_URL/{id}", genreId)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)

        verify(service).delete(genreId)
        verifyNoMoreInteractions(service)
    }
}