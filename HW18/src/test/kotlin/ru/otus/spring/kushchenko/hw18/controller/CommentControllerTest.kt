package ru.otus.spring.kushchenko.hw18.controller

import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.otus.spring.kushchenko.hw18.model.Book
import ru.otus.spring.kushchenko.hw18.model.Comment
import ru.otus.spring.kushchenko.hw18.model.User
import ru.otus.spring.kushchenko.hw18.service.CommentService
import ru.otus.spring.kushchenko.hw18.util.Util.asJsonString
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
        val user1 = User("1", "User1")
        val user2 = User("2", "User2")
        val book1 = Book("1", "Book1")
        val book2 = Book("2", "Book2")

        val comments = listOf(
            Comment("1", "Comment1", LocalDateTime.now(), user1, book1),
            Comment("2", "Comment2", LocalDateTime.now(), user2, book2)
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
    fun getPaged() {
        val page = 1
        val size = 20
        val sortBy = "name"
        val dir = "ASC"

        val pageable = PageRequest.of(
            page - 1,
            size,
            Sort(Sort.Direction.valueOf(dir), sortBy)
        )

        val userId = "1"
        val bookId = "1"
        val user = User(userId, "User1")
        val book = Book(bookId, "Book1")

        val comments = listOf(
            Comment("1", "Comment1", LocalDateTime.now(), user, book),
            Comment("2", "Comment2", LocalDateTime.now(), user, book)
        )
        val commentsPage = PageImpl(comments, pageable, comments.size.toLong())

        whenever(service.getFiltered(userId, bookId, page, size, sortBy, dir)).thenReturn(commentsPage)

        mockMvc.perform(
            get("$BASE_URL/paged")
                .param("userId", userId)
                .param("bookId", bookId)
                .param("page", page.toString())
                .param("size", size.toString())
                .param("sortBy", sortBy)
                .param("dir", dir)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().json(commentsPage.asJsonString()))
            .andReturn().response

        verify(service).getFiltered(userId, bookId, page, size, sortBy, dir)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun get() {
        val user = User("1", "User1")
        val book = Book("1", "Book1")

        val commentId = "1"
        val comment = Comment(commentId, "Comment1", LocalDateTime.now().withNano(0), user, book)

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
        val user = User("1", "User1")
        val book = Book("1", "Book1")

        val comment = Comment("1", "Comment1", LocalDateTime.now().withNano(0), user, book)

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
        val user = User("1", "User1")
        val book = Book("1", "Book1")

        val commentId = "1"
        val comment = Comment(commentId, "Comment1", LocalDateTime.now().withNano(0), user, book)

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
        val commentId = "1"

        doNothing().whenever(service).delete(commentId)
        mockMvc.perform(
            delete("$BASE_URL/{id}", commentId)
        )
            .andExpect(status().isOk)

        verify(service).delete(commentId)
        verifyNoMoreInteractions(service)
    }
}