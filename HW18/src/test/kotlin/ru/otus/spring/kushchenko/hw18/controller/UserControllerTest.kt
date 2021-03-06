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
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.otus.spring.kushchenko.hw18.model.User
import ru.otus.spring.kushchenko.hw18.service.UserService
import ru.otus.spring.kushchenko.hw18.util.Util.asJsonString

/**
 * Created by Елена on Авг., 2018
 */
@ExtendWith(SpringExtension::class)
@WebMvcTest(UserController::class)
class UserControllerTest {
    private val BASE_URL = "/users"

    @Autowired
    private lateinit var mockMvc: MockMvc
    @MockBean
    private lateinit var service: UserService

    @Test
    fun getAll() {
        val users = listOf(
            User("1", "User1"),
            User("2", "User2")
        )

        whenever(service.getAll()).thenReturn(users)

        mockMvc.perform(get(BASE_URL))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().json(users.asJsonString()))
            .andReturn().response


        verify(service).getAll()
        verifyNoMoreInteractions(service)
    }

    @Test
    fun get() {
        val userId = "1"
        val user = User(userId, "User1")

        whenever(service.get(userId)).thenReturn(user)

        mockMvc.perform(get("$BASE_URL/{id}", userId))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().json(user.asJsonString()))
            .andReturn().response

        verify(service).get(userId)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun create() {
        val user = User("1", "User1")

        whenever(service.create(user)).thenReturn(user)

        mockMvc.perform(
            post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(user.asJsonString())
        ).andDo(::print)
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().json(user.asJsonString()))
            .andReturn().response

        verify(service).create(user)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun update() {
        val userId = "1"
        val user = User(userId, "User1")

        whenever(service.update(user)).thenReturn(user)

        mockMvc.perform(
            put("$BASE_URL/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(user.asJsonString())
        ).andDo(::print)
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().json(user.asJsonString()))
            .andReturn().response

        verify(service).update(user)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun delete() {
        val userId = "1"

        doNothing().whenever(service).delete(userId)
        mockMvc.perform(
            delete("$BASE_URL/{id}", userId)
        )
            .andExpect(status().isOk)

        verify(service).delete(userId)
        verifyNoMoreInteractions(service)
    }
}