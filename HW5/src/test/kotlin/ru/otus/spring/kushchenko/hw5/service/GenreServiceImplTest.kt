package ru.otus.spring.kushchenko.hw5.service

import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.otus.spring.kushchenko.hw5.model.Genre
import ru.otus.spring.kushchenko.hw5.repository.GenreRepository

/**
 * Created by Елена on Авг., 2018
 */
internal class GenreServiceImplTest {
    private val repository: GenreRepository = mock()
    private val service = GenreServiceImpl(repository)

    @Test
    fun getAll() {
        val expected = listOf(
            Genre(1, "Genre1"),
            Genre(2, "Genre2")
        )

        whenever(repository.getAll()).thenReturn(expected)

        assertEquals(expected, service.getAll())

        verify(repository).getAll()
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun get() {
        val expectedId = 1
        val expected = Genre(expectedId, "Genre1")

        whenever(repository.get(expectedId)).thenReturn(expected)

        assertEquals(expected, service.get(expectedId))

        verify(repository).get(expectedId)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun create() {
        val expected = Genre(1, "Genre1")

        whenever(repository.create(expected)).thenReturn(expected)

        assertEquals(expected, service.create(expected))

        verify(repository).create(expected)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun update() {
        val expectedId = 1
        val expected = Genre(expectedId, "Genre1")

        whenever(repository.update(expectedId, expected)).thenReturn(expected)

        assertEquals(expected, service.update(expectedId, expected))

        verify(repository).update(expectedId, expected)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun delete() {
        val expectedId = 1

        doNothing().whenever(repository).delete(expectedId)

        service.delete(expectedId)

        verify(repository).delete(expectedId)
        verifyNoMoreInteractions(repository)
    }
}