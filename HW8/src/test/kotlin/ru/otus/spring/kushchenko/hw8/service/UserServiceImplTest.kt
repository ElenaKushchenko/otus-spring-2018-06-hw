package ru.otus.spring.kushchenko.hw8.service

import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.otus.spring.kushchenko.hw8.entity.User
import java.lang.IllegalArgumentException
import java.util.*

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

        whenever(repository.findAll()).thenReturn(users)

        assertEquals(users, service.getAll())

        verify(repository).findAll()
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun get() {
        val userId = 1
        val user = User(userId, "Reader1")

        whenever(repository.findById(userId)).thenReturn(Optional.of(user))

        assertEquals(user, service.get(userId))

        verify(repository).findById(userId)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun gettingShouldThrowExceptionBecauseUserIsNotExists() {
        val userId = 100500

        whenever(repository.findById(userId)).thenReturn(Optional.empty())

        Assertions.assertThatThrownBy { service.get(userId) }
            .isInstanceOf(IllegalArgumentException::class.java)

        verify(repository).findById(userId)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun create() {
        val user = User(1, "Reader1")
        val userRequest = user.copy(id = null)

        whenever(repository.save(userRequest)).thenReturn(user)

        assertEquals(user, service.create(userRequest))

        verify(repository).save(userRequest)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun update() {
        val userId = 1
        val user = User(userId, "Reader1")

        whenever(repository.existsById(userId)).thenReturn(true)
        whenever(repository.save(user)).thenReturn(user)

        assertEquals(user, service.update(user))

        verify(repository).existsById(userId)
        verify(repository).save(user)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun updatingShouldThrowExceptionBecauseUserIdIsNull() {
        val user = User(name = "User1")

        Assertions.assertThatThrownBy { service.update(user) }
            .isInstanceOf(NullPointerException::class.java)

        verifyNoMoreInteractions(repository)
    }

    @Test
    fun updatingShouldThrowExceptionBecauseUserIsNotExists() {
        val userId = 100500
        val user = User(id = userId, name = "User100500")

        whenever(repository.existsById(userId)).thenReturn(false)

        Assertions.assertThatThrownBy { service.update(user) }
            .isInstanceOf(IllegalArgumentException::class.java)

        verify(repository).existsById(userId)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun delete() {
        val userId = 1

        doNothing().whenever(repository).deleteById(userId)

        service.delete(userId)

        verify(repository).deleteById(userId)
        verifyNoMoreInteractions(repository)
    }
}