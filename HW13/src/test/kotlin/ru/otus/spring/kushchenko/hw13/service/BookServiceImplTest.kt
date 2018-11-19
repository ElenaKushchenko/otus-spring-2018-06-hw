package ru.otus.spring.kushchenko.hw13.service

import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit.jupiter.SpringExtension
import ru.otus.spring.kushchenko.hw13.model.Book
import ru.otus.spring.kushchenko.hw13.model.Comment
import ru.otus.spring.kushchenko.hw13.model.ShortBook
import ru.otus.spring.kushchenko.hw13.repository.BookRepository
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest
class BookServiceImplTest {
    @MockBean
    private lateinit var bookRepository: BookRepository
    @Autowired
    private lateinit var service: BookService

    @Test
    @WithMockUser
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

    @Test
    @WithMockUser
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

        val author1 = "Author1"
        val author2 = "Author2"
        val author3 = "Author3"
        val genre1 = "Genre1"
        val genre2 = "Genre2"
        val genre3 = "Genre3"

        val books = listOf(
            Book(
                id = "1",
                name = "Book1",
                originalName = "Book1 Original Name",
                paperback = 111,
                authors = listOf(author1, author2),
                genres = listOf(genre1, genre2)
            ),
            Book(
                id = "2",
                name = "Book2",
                originalName = "Book2 Original Name",
                paperback = 222,
                authors = listOf(author2, author3),
                genres = listOf(genre2, genre3)
            )
        )
        val booksPage = PageImpl(books, pageable, books.size.toLong())

        whenever(bookRepository.findAll(pageable)).thenReturn(booksPage)

        assertEquals(booksPage, service.getPaged(page, size, sortBy, dir))

        verify(bookRepository).findAll(pageable)
        verifyNoMoreInteractions(bookRepository)
    }

    @Nested
    @DisplayName("Tests for get() method")
    inner class Get {

        @Test
        @WithMockUser
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
        @WithMockUser
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
        @WithMockUser(authorities = ["ADMIN"])
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
        @WithMockUser(authorities = ["ADMIN"])
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

        @Test
        @WithMockUser(authorities = ["USER"])
        fun shouldFailBecauseNoPermissions() {
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

            Assertions.assertThatThrownBy { service.create(book) }
                .isInstanceOf(AccessDeniedException::class.java)

            verifyNoMoreInteractions(bookRepository)
        }
    }

    @Nested
    @DisplayName("Tests for delete() method")
    inner class Delete {

        @Test
        @WithMockUser(authorities = ["ADMIN"])
        fun shouldPassSuccessfully() {
            val bookId = "1"

            doNothing().whenever(bookRepository).deleteById(bookId)

            service.delete(bookId)

            verify(bookRepository).deleteById(bookId)
            verifyNoMoreInteractions(bookRepository)
        }

        @Test
        @WithMockUser(authorities = ["USER"])
        fun shouldFailBecauseNoPermissions() {
            val bookId = "1"

            Assertions.assertThatThrownBy { service.delete(bookId) }
                .isInstanceOf(AccessDeniedException::class.java)

            verifyNoMoreInteractions(bookRepository)
        }
    }
}