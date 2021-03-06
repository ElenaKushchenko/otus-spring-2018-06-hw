package ru.otus.spring.kushchenko.adminclient.service

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import ru.otus.spring.kushchenko.adminclient.repository.GenreRepository

class GenreServiceImplTest {
    private val repository: GenreRepository = mock()
    private val service = GenreServiceImpl(repository)

    @Test
    fun getAll() {
        val genres = listOf("Genre1", "Genre2")

        whenever(repository.getAll()).thenReturn(genres)

        assertEquals(genres, service.getAll())

        verify(repository).getAll()
        verifyNoMoreInteractions(repository)
    }
}