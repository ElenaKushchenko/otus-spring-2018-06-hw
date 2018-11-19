package ru.otus.spring.kushchenko.hw13.controller

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view
import ru.otus.spring.kushchenko.hw13.service.CommentService

/**
 * Created by Елена on Нояб., 2018
 */
@ExtendWith(SpringExtension::class)
@WebMvcTest(CommentController::class)
class CommentControllerTest {
    private val BASE_URL = "/books/{bookId}/comments"

    @Autowired
    private lateinit var mockMvc: MockMvc
    @MockBean
    private lateinit var service: CommentService

    @AfterEach
    fun clean() {
        reset(service)
    }

    @Nested
    @DisplayName("Tests for create() method")
    inner class Create {

        @Test
        @WithMockUser(authorities = ["USER"])
        fun shouldPassSuccessfully() {
            val bookId = "1"
            val comment = "Comment"

            mockMvc.perform(
                post(BASE_URL, bookId)
                    .param("text", comment)
            )
                .andExpect(status().isFound)
                .andExpect(redirectedUrl("/books/$bookId"))
                .andReturn().response

            verify(service).create(any(), any())
            verifyNoMoreInteractions(service)
        }

        @Test
        @WithMockUser(authorities = ["ADMIN"])
        fun shouldFailBecauseNoPermissions() {
            val bookId = "1"
            val comment = "Comment"

            whenever(service.create(any(), any())).thenThrow(AccessDeniedException(""))

            mockMvc.perform(
                post(BASE_URL, bookId)
                    .param("text", comment)
            )
                .andExpect(status().isInternalServerError)
                .andExpect(view().name("error"))
                .andReturn().response

            verify(service).create(any(), any())
            verifyNoMoreInteractions(service)
        }
    }
}