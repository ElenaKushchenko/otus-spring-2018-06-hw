package ru.otus.spring.kushchenko.hw12.service

import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import ru.otus.spring.kushchenko.hw12.model.Book
import ru.otus.spring.kushchenko.hw12.model.Comment
import ru.otus.spring.kushchenko.hw12.model.ShortBook
import ru.otus.spring.kushchenko.hw12.repository.BookRepository
import java.util.*

class BookServiceImplTest {
    private val bookRepository: BookRepository = mock()
    private val service = BookServiceImpl(bookRepository)

    @Test
    fun getAll() {
        val author1 = "Author1"
        val author2 = "Author2"
        val author3 = "Author3"
        val genre1 = "Genre1"
        val genre2 = "Genre2"
        val genre3 = "Genre3"

        val books = listOf(
            ShortBook(
                id = "1",
                name = "Book1",
                authors = listOf(author1, author2),
                genres = listOf(genre1, genre2)
            ),
            ShortBook(
                id = "2",
                name = "Book2",
                authors = listOf(author2, author3),
                genres = listOf(genre2, genre3)
            )
        )

        whenever(bookRepository.findAllShortBooks()).thenReturn(books)

        assertEquals(books, service.getAll())

        verify(bookRepository).findAllShortBooks()
        verifyNoMoreInteractions(bookRepository)
    }

    @Nested
    @DisplayName("Tests for get() method")
    inner class Get {

        @Test
        fun shouldPassSuccessfully() {
            val author = "Author1"
            val genre = "Genre1"
            val comment1 = Comment(username = "User1", text = "Comment1")
            val comment2 = Comment(username = "User2", text = "Comment2")

            val bookId = "1"
            val book = Book(
                id = bookId,
                name = "Book1",
                originalName = "Book1 Original Name",
                paperback = 111,
                authors = listOf(author),
                genres = listOf(genre),
                comments = listOf(comment1, comment2)
            )

            whenever(bookRepository.findById(bookId)).thenReturn(Optional.of(book))

            assertEquals(book, service.get(bookId))

            verify(bookRepository).findById(bookId)
            verifyNoMoreInteractions(bookRepository)
        }

        @Test
        fun shouldFailBecauseBookDoesNotExist() {
            val bookId = "100500"

            whenever(bookRepository.findById(bookId)).thenReturn(Optional.empty())

            Assertions.assertThatThrownBy { service.get(bookId) }
                .isInstanceOf(IllegalArgumentException::class.java)

            verify(bookRepository).findById(bookId)
            verifyNoMoreInteractions(bookRepository)
        }
    }

    @Nested
    @DisplayName("Tests for create() method")
    inner class Create {

        @Test
        fun shouldPassSuccessfully() {
            val author = "Author1"
            val genre = "Genre1"

            val bookId = "1"
            val book = Book(
                id = bookId,
                name = "Book1",
                originalName = "Book1 Original Name",
                paperback = 111,
                authors = listOf(author),
                genres = listOf(genre)
            )

            whenever(bookRepository.existsById(bookId)).thenReturn(false)
            whenever(bookRepository.save(book)).thenReturn(book)

            assertEquals(book, service.create(book))

            verify(bookRepository).existsById(bookId)
            verify(bookRepository).save(book)
            verifyNoMoreInteractions(bookRepository)
        }

        @Test
        fun shouldFailBecauseBookAlreadyExists() {
            val author = "Author1"
            val genre = "Genre1"

            val bookId = "1"
            val book = Book(
                id = bookId,
                name = "Book1",
                originalName = "Book1 Original Name",
                paperback = 111,
                authors = listOf(author),
                genres = listOf(genre)
            )

            whenever(bookRepository.existsById(bookId)).thenReturn(true)

            Assertions.assertThatThrownBy { service.create(book) }
                .isInstanceOf(IllegalArgumentException::class.java)

            verify(bookRepository).existsById(bookId)
            verifyNoMoreInteractions(bookRepository)
        }
    }

    @Test
    fun delete() {
        val bookId = "1"

        doNothing().whenever(bookRepository).deleteById(bookId)

        service.delete(bookId)

        verify(bookRepository).deleteById(bookId)
        verifyNoMoreInteractions(bookRepository)
    }
}