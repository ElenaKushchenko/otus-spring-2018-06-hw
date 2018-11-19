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
import ru.otus.spring.kushchenko.hw13.service.AuthorService

/**
 * Created by Елена on Нояб., 2018
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
    @WithMockUser
    fun getAll() {
        val author1 = "Author1"
        val author2 = "Author2"
        val authors = listOf(author1, author2)

        whenever(service.getAll()).thenReturn(authors)

        mockMvc.perform(get(BASE_URL))
            .andExpect(status().isOk)
            .andExpect(view().name("authors"))
            .andExpect(model().attribute("authors", hasItem(author1)))
            .andExpect(model().attribute("authors", hasItem(author2)))
            .andReturn().response

        verify(service).getAll()
        verifyNoMoreInteractions(service)
    }
}