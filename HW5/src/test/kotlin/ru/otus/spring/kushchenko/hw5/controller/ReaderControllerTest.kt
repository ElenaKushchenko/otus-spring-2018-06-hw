package ru.otus.spring.kushchenko.hw5.controller

import com.jayway.jsonassert.impl.matcher.IsCollectionWithSize
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
import ru.otus.spring.kushchenko.hw5.model.Reader
import ru.otus.spring.kushchenko.hw5.service.ReaderService
import ru.otus.spring.kushchenko.hw5.util.Util.asJsonString

/**
 * Created by Елена on Авг., 2018
 */
@ExtendWith(SpringExtension::class)
@WebMvcTest(ReaderController::class)
class ReaderControllerTest {
    private val BASE_URL = "/readers"

    @Autowired
    private lateinit var mockMvc: MockMvc
    @MockBean
    private lateinit var service: ReaderService

    @Test
    fun getAll() {
        val expected = listOf(
            Reader(1, "Reader1"),
            Reader(2, "Reader2")
        )

        whenever(service.getAll()).thenReturn(expected)

        mockMvc.perform(get(BASE_URL))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$", IsCollectionWithSize.hasSize(expected.size)))
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
        val readerId = 1
        val reader = Reader(readerId, "Reader1")

        whenever(service.get(readerId)).thenReturn(reader)

        mockMvc.perform(get("$BASE_URL/{id}", readerId))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.`is`(reader.id)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.`is`(reader.name)))

        verify(service).get(readerId)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun create() {
        val reader = Reader(1, "Reader1")

        whenever(service.create(reader)).thenReturn(reader)

        mockMvc.perform(
            post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(reader.asJsonString())
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.`is`(reader.id)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.`is`(reader.name)))

        verify(service).create(reader)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun update() {
        val readerId = 1
        val reader = Reader(readerId, "Reader1")

        whenever(service.update(readerId, reader)).thenReturn(reader)

        mockMvc.perform(
            put("$BASE_URL/{id}", readerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(reader.asJsonString())
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.`is`(reader.id)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.`is`(reader.name)))

        verify(service).update(readerId, reader)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun delete() {
        val readerId = 1

        doNothing().whenever(service).delete(readerId)
        mockMvc.perform(
            delete("$BASE_URL/{id}", readerId)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)

        verify(service).delete(readerId)
        verifyNoMoreInteractions(service)
    }
}