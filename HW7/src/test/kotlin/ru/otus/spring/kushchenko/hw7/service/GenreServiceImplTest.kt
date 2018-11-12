package ru.otus.spring.kushchenko.hw7.service

import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.otus.spring.kushchenko.hw7.model.Genre
import ru.otus.spring.kushchenko.hw7.repository.GenreRepository
import java.lang.IllegalArgumentException
import java.util.*

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

        whenever(repository.findAll()).thenReturn(genres)

        assertEquals(genres, service.getAll())

        verify(repository).findAll()
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun get() {
        val genreId = 1
        val genre = Genre(genreId, "Genre1")

        whenever(repository.findById(genreId)).thenReturn(Optional.of(genre))

        assertEquals(genre, service.get(genreId))

        verify(repository).findById(genreId)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun gettingShouldThrowExceptionBecauseGenreIsNotExists() {
        val genreId = 100500

        whenever(repository.findById(genreId)).thenReturn(Optional.empty())

        Assertions.assertThatThrownBy { service.get(genreId) }
            .isInstanceOf(IllegalArgumentException::class.java)

        verify(repository).findById(genreId)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun create() {
        val genre = Genre(1, "Genre1")
        val genreRequest = genre.copy(id = null)

        whenever(repository.save(genreRequest)).thenReturn(genre)

        assertEquals(genre, service.create(genreRequest))

        verify(repository).save(genreRequest)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun update() {
        val genreId = 1
        val genre = Genre(genreId, "Genre1")

        whenever(repository.existsById(genreId)).thenReturn(true)
        whenever(repository.save(genre)).thenReturn(genre)

        assertEquals(genre, service.update(genre))

        verify(repository).existsById(genreId)
        verify(repository).save(genre)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun updatingShouldThrowExceptionBecauseGenreIdIsNull() {
        val genre = Genre(name = "Genre1")

        assertThatThrownBy { service.update(genre) }
            .isInstanceOf(NullPointerException::class.java)

        verifyNoMoreInteractions(repository)
    }

    @Test
    fun updatingShouldThrowExceptionBecauseGenreIsNotExists() {
        val genreId = 100500
        val genre = Genre(id = genreId, name = "Genre100500")

        whenever(repository.existsById(genreId)).thenReturn(false)

        assertThatThrownBy { service.update(genre) }
            .isInstanceOf(IllegalArgumentException::class.java)

        verify(repository).existsById(genreId)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun delete() {
        val genreId = 1

        doNothing().whenever(repository).deleteById(genreId)

        service.delete(genreId)

        verify(repository).deleteById(genreId)
        verifyNoMoreInteractions(repository)
    }
}