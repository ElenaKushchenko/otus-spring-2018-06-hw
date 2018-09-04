package ru.otus.spring.kushchenko.hw6.service

import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.otus.spring.kushchenko.hw6.entity.User
import ru.otus.spring.kushchenko.hw6.repository.UserRepository

/**
 * Created by Елена on Авг., 2018
 */
internal class UserServiceImplTest {
    private val repository: UserRepository = mock()
    private val service = UserServiceImpl(repository)

    @Test
    fun getAll() {
        val users = listOf(
            User(1, "Reader1"),
            User(2, "Reader2")
        )

        whenever(repository.getAll()).thenReturn(users)

        assertEquals(users, service.getAll())

        verify(repository).getAll()
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun get() {
        val userId = 1
        val users = User(userId, "Reader1")

        whenever(repository.get(userId)).thenReturn(users)

        assertEquals(users, service.get(userId))

        verify(repository).get(userId)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun create() {
        val user = User(1, "Reader1")
        val userRequest = user.copy(id = null)

        whenever(repository.create(userRequest)).thenReturn(user)

        assertEquals(user, service.create(userRequest))

        verify(repository).create(userRequest)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun update() {
        val userId = 1
        val user = User(userId, "Reader1")
        val userRequest = user.copy(id = null)

        whenever(repository.update(userId, userRequest)).thenReturn(user)

        assertEquals(user, service.update(userId, userRequest))

        verify(repository).update(userId, userRequest)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun delete() {
        val userId = 1

        doNothing().whenever(repository).delete(userId)

        service.delete(userId)

        verify(repository).delete(userId)
        verifyNoMoreInteractions(repository)
    }
}