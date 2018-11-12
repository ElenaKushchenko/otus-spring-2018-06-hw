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
import ru.otus.spring.kushchenko.hw7.model.Comment
import ru.otus.spring.kushchenko.hw7.model.User
import ru.otus.spring.kushchenko.hw7.service.CommentService
import ru.otus.spring.kushchenko.hw7.util.Util.asJsonString
import java.time.LocalDateTime

/**
 * Created by Елена on Авг., 2018
 */
@ExtendWith(SpringExtension::class)
@WebMvcTest(CommentController::class)
class CommentControllerTest {
    private val BASE_URL = "/comments"

    @Autowired
    private lateinit var mockMvc: MockMvc
    @MockBean
    private lateinit var service: CommentService

    @Test
    fun getAll() {
        val bookId = 1
        val user1 = User(1, "User1")
        val user2 = User(2, "User2")

        val comments = listOf(
            Comment(1, "Comment1", LocalDateTime.now(), user1, bookId),
            Comment(2, "Comment2", LocalDateTime.now(), user2, bookId)
        )

        whenever(service.getAll()).thenReturn(comments)

        mockMvc.perform(get(BASE_URL))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().json(comments.asJsonString()))
            .andReturn().response

        verify(service).getAll()
        verifyNoMoreInteractions(service)
    }

    @Test
    fun get() {
        val bookId = 1
        val user = User(1, "User1")

        val commentId = 1
        val comment = Comment(commentId, "Comment1", LocalDateTime.now().withNano(0), user, bookId)

        whenever(service.get(commentId)).thenReturn(comment)

        mockMvc.perform(get("$BASE_URL/{id}", commentId))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().json(comment.asJsonString()))
            .andReturn().response

        verify(service).get(commentId)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun create() {
        val bookId = 1
        val user = User(1, "User1")
        val comment = Comment(1, "Comment1", LocalDateTime.now().withNano(0), user, bookId)

        whenever(service.create(comment)).thenReturn(comment)

        mockMvc.perform(
            post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(comment.asJsonString())
        ).andDo(::print)
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().json(comment.asJsonString()))
            .andReturn().response

        verify(service).create(comment)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun update() {
        val bookId = 1
        val user = User(1, "User1")

        val commentId = 1
        val comment = Comment(1, "Comment1", LocalDateTime.now().withNano(0), user, bookId)

        whenever(service.update(comment)).thenReturn(comment)

        mockMvc.perform(
            put("$BASE_URL/{id}", commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(comment.asJsonString())
        ).andDo(::print)
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().json(comment.asJsonString()))
            .andReturn().response

        verify(service).update(comment)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun delete() {
        val commentId = 1

        doNothing().whenever(service).delete(commentId)
        mockMvc.perform(
            delete("$BASE_URL/{id}", commentId)
        )
            .andExpect(status().isOk)

        verify(service).delete(commentId)
        verifyNoMoreInteractions(service)
    }
}