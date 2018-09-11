package ru.otus.spring.kushchenko.hw6.service

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.otus.spring.kushchenko.hw6.entity.Genre
import ru.otus.spring.kushchenko.hw6.repository.GenreRepository

/**
 * Created by Елена on Авг., 2018
 */
internal class GenreServiceImplTest {
    private val repository: GenreRepository = mock()
    private val service = GenreServiceImpl(repository)

    @Test
    fun getAll() {
        val genres = listOf(
            Genre(1, "Genre1"),
            Genre(2, "Genre2")
        )

        whenever(repository.getAll()).thenReturn(genres)

        assertEquals(genres, service.getAll())

        verify(repository).getAll()
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun get() {
        val genreId = 1
        val genres = Genre(genreId, "Genre1")

        whenever(repository.get(genreId)).thenReturn(genres)

        assertEquals(genres, service.get(genreId))

        verify(repository).get(genreId)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun create() {
        val genre = Genre(1, "Genre1")
        val genreRequest = genre.copy(id = null)

        whenever(repository.create(genreRequest)).thenReturn(genre)

        assertEquals(genre, service.create(genreRequest))

        verify(repository).create(genreRequest)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun update() {
        val genreId = 1
        val genre = Genre(genreId, "Genre1")
        val genreRequest = genre.copy(id = null)

        whenever(repository.update(genreId, genreRequest)).thenReturn(genre)

        assertEquals(genre, service.update(genreId, genreRequest))

        verify(repository).update(genreId, genreRequest)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun delete() {
        val genreId = 1

        doNothing().whenever(repository).delete(genreId)

        service.delete(genreId)

        verify(repository).delete(genreId)
        verifyNoMoreInteractions(repository)
    }
}