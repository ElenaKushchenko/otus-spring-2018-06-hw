package ru.otus.spring.kushchenko.hw13.controller

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.CoreMatchers.hasItem
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view
import ru.otus.spring.kushchenko.hw13.service.GenreService

/**
 * Created by Елена on Нояб., 2018
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
    @WithMockUser
    fun getAll() {
        val genre1 = "Genre1"
        val genre2 = "Genre2"
        val genres = listOf(genre1, genre2)

        whenever(service.getAll()).thenReturn(genres)

        mockMvc.perform(get(BASE_URL))
            .andExpect(status().isOk)
            .andExpect(view().name("genres"))
            .andExpect(model().attribute("genres", hasItem(genre1)))
            .andExpect(model().attribute("genres", hasItem(genre2)))
            .andReturn().response

        verify(service).getAll()
        verifyNoMoreInteractions(service)
    }
}