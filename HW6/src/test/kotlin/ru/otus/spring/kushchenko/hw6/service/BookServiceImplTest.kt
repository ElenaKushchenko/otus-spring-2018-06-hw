package ru.otus.spring.kushchenko.hw6.service

import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.otus.spring.kushchenko.hw6.entity.Author
import ru.otus.spring.kushchenko.hw6.entity.Book
import ru.otus.spring.kushchenko.hw6.entity.Genre
import ru.otus.spring.kushchenko.hw6.entity.User
import ru.otus.spring.kushchenko.hw6.repository.BookRepository

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
        val reader1 = User(1, "Reader1")
        val reader2 = User(2, "Reader2")

        val books = listOf(
            Book(1, "Book1", "Book1 Original Name", 111, listOf(author1, author2), listOf(genre1, genre2), reader1),
            Book(2, "Book2", "Book2 Original Name", 222, listOf(author2, author3), listOf(genre2, genre3), reader2)
        )

        whenever(repository.getAll()).thenReturn(books)

        assertEquals(books, service.getAll())

        verify(repository).getAll()
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun get() {
        val author = Author(1, "Author1")
        val genre = Genre(1, "Genre1")
        val reader = User(1, "Reader1")

        val bookId = 1
        val books = Book(bookId, "Book1", "Book1 Original Name", 111, listOf(author), listOf(genre), reader)

        whenever(repository.get(bookId)).thenReturn(books)

        assertEquals(books, service.get(bookId))

        verify(repository).get(bookId)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun create() {
        val author = Author(1, "Author1")
        val genre = Genre(1, "Genre1")
        val reader = User(1, "Reader1")

        val book = Book(1, "Book1", "Book1 Original Name", 111, listOf(author), listOf(genre), reader)
        val bookRequest = book.copy(id = null)

        whenever(repository.create(bookRequest)).thenReturn(book)

        assertEquals(book, service.create(bookRequest))

        verify(repository).create(bookRequest)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun update() {
        val author = Author(1, "Author1")
        val genre = Genre(1, "Genre1")
        val reader = User(1, "Reader1")

        val bookId = 1
        val book = Book(bookId, "Book1", "Book1 Original Name", 111, listOf(author), listOf(genre), reader)
        val bookRequest = book.copy(id = null)

        whenever(repository.update(bookId, bookRequest)).thenReturn(book)

        assertEquals(book, service.update(bookId, bookRequest))

        verify(repository).update(bookId, bookRequest)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun delete() {
        val bookId = 1

        doNothing().whenever(repository).delete(bookId)

        service.delete(bookId)

        verify(repository).delete(bookId)
        verifyNoMoreInteractions(repository)
    }
}