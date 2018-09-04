package ru.otus.spring.kushchenko.hw5.service

import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.otus.spring.kushchenko.hw5.model.Author
import ru.otus.spring.kushchenko.hw5.model.Book
import ru.otus.spring.kushchenko.hw5.model.Genre
import ru.otus.spring.kushchenko.hw5.model.Reader
import ru.otus.spring.kushchenko.hw5.repository.BookRepository

/**
 * Created by Елена on Авг., 2018
 */
internal class BookServiceImplTest {
    private val repository: BookRepository = mock()
    private val service = BookServiceImpl(repository)

    @Test
    fun getAll() {
        val author1 = Author(1, "Author1")
        val author2 = Author(2, "Author2")
        val author3 = Author(3, "Author3")
        val genre1 = Genre(1, "Genre1")
        val genre2 = Genre(2, "Genre2")
        val genre3 = Genre(3, "Genre3")
        val reader1 = Reader(1, "Reader1")
        val reader2 = Reader(2, "Reader2")

        val expected = listOf(
            Book(1, "Book1", "Book1 Original Name", 111, listOf(author1, author2), listOf(genre1, genre2), reader1),
            Book(2, "Book2", "Book2 Original Name", 222, listOf(author2, author3), listOf(genre2, genre3), reader2)
        )

        whenever(repository.getAll()).thenReturn(expected)

        assertEquals(expected, service.getAll())

        verify(repository).getAll()
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun get() {
        val author = Author(1, "Author1")
        val genre = Genre(1, "Genre1")
        val reader = Reader(1, "Reader1")

        val expectedId = 1
        val expected = Book(expectedId, "Book1", "Book1 Original Name", 111, listOf(author), listOf(genre), reader)

        whenever(repository.get(expectedId)).thenReturn(expected)

        assertEquals(expected, service.get(expectedId))

        verify(repository).get(expectedId)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun create() {
        val author = Author(1, "Author1")
        val genre = Genre(1, "Genre1")
        val reader = Reader(1, "Reader1")

        val expected = Book(1, "Book1", "Book1 Original Name", 111, listOf(author), listOf(genre), reader)

        whenever(repository.create(expected)).thenReturn(expected)

        assertEquals(expected, service.create(expected))

        verify(repository).create(expected)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun update() {
        val author = Author(1, "Author1")
        val genre = Genre(1, "Genre1")
        val reader = Reader(1, "Reader1")

        val expectedId = 1
        val expected = Book(expectedId, "Book1", "Book1 Original Name", 111, listOf(author), listOf(genre), reader)

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