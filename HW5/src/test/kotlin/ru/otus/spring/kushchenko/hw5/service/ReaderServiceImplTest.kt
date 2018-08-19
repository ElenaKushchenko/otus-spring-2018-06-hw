package ru.otus.spring.kushchenko.hw5.service

import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.otus.spring.kushchenko.hw5.model.Reader
import ru.otus.spring.kushchenko.hw5.repository.ReaderRepository

/**
 * Created by Елена on Авг., 2018
 */
internal class ReaderServiceImplTest {
    private val repository: ReaderRepository = mock()
    private val service = ReaderServiceImpl(repository)

    @Test
    fun getAll() {
        val expected = listOf(
            Reader(1, "Reader1"),
            Reader(2, "Reader2")
        )

        whenever(repository.getAll()).thenReturn(expected)

        assertEquals(expected, service.getAll())

        verify(repository).getAll()
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun get() {
        val expectedId = 1
        val expected = Reader(expectedId, "Reader1")

        whenever(repository.get(expectedId)).thenReturn(expected)

        assertEquals(expected, service.get(expectedId))

        verify(repository).get(expectedId)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun create() {
        val expected = Reader(1, "Reader1")

        whenever(repository.create(expected)).thenReturn(expected)

        assertEquals(expected, service.create(expected))

        verify(repository).create(expected)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun update() {
        val expectedId = 1
        val expected = Reader(expectedId, "Reader1")

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