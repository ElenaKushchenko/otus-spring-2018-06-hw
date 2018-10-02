package ru.otus.spring.kushchenko.hw8.service

import com.nhaarman.mockito_kotlin.*
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import ru.otus.spring.kushchenko.hw8.model.Author
import ru.otus.spring.kushchenko.hw8.model.Book
import ru.otus.spring.kushchenko.hw8.model.User
import ru.otus.spring.kushchenko.hw8.repository.BookRepository
import ru.otus.spring.kushchenko.hw8.repository.UserRepository
import java.time.LocalDate
import java.util.*

class BookServiceImplTest {
    private val bookRepository: BookRepository = mock()
    private val userRepository: UserRepository = mock()
    private val service = BookServiceImpl(bookRepository, userRepository)

    @Test
    fun getAll() {
        val author1 = Author("Author1", "USA", LocalDate.parse("1900-03-01"))
        val author2 = Author("Author2", "Russia", LocalDate.parse("1900-02-01"))
        val author3 = Author("Author3", "UK", LocalDate.parse("1900-01-01"))
        val genre1 = "Genre1"
        val genre2 = "Genre2"
        val genre3 = "Genre3"
        val user1 = User("1", "User1")
        val user2 = User("2", "User2")

        val books = listOf(
            Book(
                id = "1",
                name = "Book1",
                originalName = "Book1 Original Name",
                paperback = 111,
                authors = listOf(author1, author2),
                genres = listOf(genre1, genre2),
                user = user1
            ),
            Book(
                id = "2",
                name = "Book2",
                originalName = "Book2 Original Name",
                paperback = 222,
                authors = listOf(author2, author3),
                genres = listOf(genre2, genre3),
                user = user2
            )
        )

        whenever(bookRepository.findAll()).thenReturn(books)

        assertEquals(books, service.getAll())

        verify(bookRepository).findAll()
        verifyNoMoreInteractions(bookRepository)
        verifyNoMoreInteractions(userRepository)
    }

    @Test
    fun getPaged() {
        val page = 1
        val size = 20
        val sortBy = "name"
        val dir = "ASC"

        val pageable = PageRequest.of(
            page - 1,
            size,
            Sort(Sort.Direction.valueOf(dir), sortBy)
        )

        val author1 = Author("Author1", "USA", LocalDate.parse("1900-03-01"))
        val author2 = Author("Author2", "Russia", LocalDate.parse("1900-02-01"))
        val author3 = Author("Author3", "UK", LocalDate.parse("1900-01-01"))
        val genre1 = "Genre1"
        val genre2 = "Genre2"
        val genre3 = "Genre3"
        val user1 = User("1", "User1")
        val user2 = User("2", "User2")

        val books = listOf(
            Book(
                id = "1",
                name = "Book1",
                originalName = "Book1 Original Name",
                paperback = 111,
                authors = listOf(author1, author2),
                genres = listOf(genre1, genre2),
                user = user1
            ),
            Book(
                id = "2",
                name = "Book2",
                originalName = "Book2 Original Name",
                paperback = 222,
                authors = listOf(author2, author3),
                genres = listOf(genre2, genre3),
                user = user2
            )
        )
        val booksPage = PageImpl(books, pageable, books.size.toLong())

        whenever(bookRepository.findAll(pageable)).thenReturn(booksPage)

        assertEquals(booksPage, service.getPaged(page, size, sortBy, dir))

        verify(bookRepository).findAll(pageable)
        verifyNoMoreInteractions(bookRepository)
        verifyNoMoreInteractions(userRepository)
    }

    @Nested
    @DisplayName("Tests for get() method")
    inner class Get {

        @Test
        fun shouldPassSuccessfully() {
            val author = Author("Author1", "USA", LocalDate.parse("1900-03-01"))
            val genre = "Genre1"
            val user = User("1", "User1")

            val bookId = "1"
            val book = Book(
                id = bookId,
                name = "Book1",
                originalName = "Book1 Original Name",
                paperback = 111,
                authors = listOf(author),
                genres = listOf(genre),
                user = user
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
            verifyNoMoreInteractions(userRepository)
        }
    }

    @Nested
    @DisplayName("Tests for create() method")
    inner class Create {

        @Test
        fun shouldPassSuccessfully() {
            val author = Author("Author1", "USA", LocalDate.parse("1900-03-01"))
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
            verifyNoMoreInteractions(userRepository)
        }

        @Test
        fun shouldFailBecauseBookAlreadyExists() {
            val author = Author("Author1", "USA", LocalDate.parse("1900-03-01"))
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
            verifyNoMoreInteractions(userRepository)
        }

        @Test
        fun shouldFailBecauseUserDoesNotExist() {
            val author = Author("Author1", "USA", LocalDate.parse("1900-03-01"))
            val genre = "Genre1"

            val userId = "1"
            val user = User(userId, "User1")

            val bookId = "1"
            val book = Book(
                id = bookId,
                name = "Book1",
                originalName = "Book1 Original Name",
                paperback = 111,
                authors = listOf(author),
                genres = listOf(genre),
                user = user
            )

            whenever(bookRepository.existsById(bookId)).thenReturn(false)
            whenever(userRepository.findById(userId)).thenReturn(Optional.empty())

            Assertions.assertThatThrownBy { service.create(book) }
                .isInstanceOf(IllegalArgumentException::class.java)

            verify(bookRepository).existsById(bookId)
            verify(userRepository).findById(userId)
            verifyNoMoreInteractions(bookRepository)
            verifyNoMoreInteractions(userRepository)
        }
    }

    @Nested
    @DisplayName("Tests for update() method")
    inner class Update {

        @Test
        fun shouldPassSuccessfully() {
            val author = Author("Author1", "USA", LocalDate.parse("1900-03-01"))
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
            whenever(bookRepository.save(book)).thenReturn(book)

            assertEquals(book, service.update(book))

            verify(bookRepository).existsById(bookId)
            verify(bookRepository).save(book)
            verifyNoMoreInteractions(bookRepository)
            verifyNoMoreInteractions(userRepository)
        }

        @Test
        fun shouldFailBecauseBookDoesNotExist() {
            val author = Author("Author1", "USA", LocalDate.parse("1900-03-01"))
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

            Assertions.assertThatThrownBy { service.update(book) }
                .isInstanceOf(IllegalArgumentException::class.java)

            verify(bookRepository).existsById(bookId)
            verifyNoMoreInteractions(bookRepository)
            verifyNoMoreInteractions(userRepository)
        }

        @Test
        fun shouldFailBecauseUserDoesNotExist() {
            val author = Author("Author1", "USA", LocalDate.parse("1900-03-01"))
            val genre = "Genre1"

            val userId = "1"
            val user = User(userId, "User1")

            val bookId = "1"
            val book = Book(
                id = bookId,
                name = "Book1",
                originalName = "Book1 Original Name",
                paperback = 111,
                authors = listOf(author),
                genres = listOf(genre),
                user = user
            )

            whenever(bookRepository.existsById(bookId)).thenReturn(true)
            whenever(userRepository.findById(userId)).thenReturn(Optional.empty())

            Assertions.assertThatThrownBy { service.update(book) }
                .isInstanceOf(IllegalArgumentException::class.java)

            verify(bookRepository).existsById(bookId)
            verify(userRepository).findById(userId)
            verifyNoMoreInteractions(bookRepository)
            verifyNoMoreInteractions(userRepository)
        }

        @Test
        fun shouldFailBecauseBookIdNotSpecified() {
            val book = Book(null, "Reader1")

            Assertions.assertThatThrownBy { service.update(book) }
                .isInstanceOf(NullPointerException::class.java)

            verifyNoMoreInteractions(bookRepository)
            verifyNoMoreInteractions(userRepository)
        }
    }

    @Test
    fun delete() {
        val bookId = "1"

        doNothing().whenever(bookRepository).deleteById(bookId)

        service.delete(bookId)

        verify(bookRepository).deleteById(bookId)
        verifyNoMoreInteractions(bookRepository)
        verifyNoMoreInteractions(userRepository)
    }
}