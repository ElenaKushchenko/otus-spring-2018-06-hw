package ru.otus.spring.kushchenko.hw9.service

import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import ru.otus.spring.kushchenko.hw9.model.Author
import ru.otus.spring.kushchenko.hw9.model.Book
import ru.otus.spring.kushchenko.hw9.model.Genre
import ru.otus.spring.kushchenko.hw9.model.ShortBook
import ru.otus.spring.kushchenko.hw9.repository.BookRepository
import ru.otus.spring.kushchenko.hw9.util.Util.toShortBook
import java.lang.IllegalArgumentException
import java.util.*

/**
 * Created by Елена on Авг., 2018
 */
internal class BookServiceImplTest {
    private val repository: BookRepository = mock()
    private val service = BookServiceImpl(repository)

    @Test
    fun getAllShortBooks() {
        val author1 = Author(1, "Author1")
        val author2 = Author(2, "Author2")
        val author3 = Author(3, "Author3")
        val genre1 = Genre(1, "Genre1")
        val genre2 = Genre(2, "Genre2")
        val genre3 = Genre(3, "Genre3")

        val books = listOf(
            Book(id = 1, name = "Book1", authors = listOf(author1, author2), genres = listOf(genre1, genre2)).toShortBook(),
            Book(id = 2, name = "Book2", authors = listOf(author2, author3), genres = listOf(genre2, genre3)).toShortBook()
        )

        whenever(repository.findAllShortBooks()).thenReturn(books)

        assertEquals(books, service.getAllShortBooks())

        verify(repository).findAllShortBooks()
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun find() {
        val author1 = Author(1, "Author1")
        val author2 = Author(2, "Author2")
        val author3 = Author(3, "Author3")
        val genre1 = Genre(1, "Genre1")
        val genre2 = Genre(2, "Genre2")
        val genre3 = Genre(3, "Genre3")

        val books: List<ShortBook> = listOf(
            Book(id = 1, name = "Book1", authors = listOf(author1, author2), genres = listOf(genre1, genre2)).toShortBook(),
            Book(id = 2, name = "Book2", authors = listOf(author2, author3), genres = listOf(genre2, genre3)).toShortBook()
        )

        val bookNameFilter = "Book1"
        val authorIdFilter = 1
        val genreIdFilter = 1
        val page = 1
        val size = 10

        val pageable = PageRequest.of(page - 1, size)
        val content = PageImpl(books, pageable, books.size.toLong())

        whenever(repository.find(bookNameFilter, authorIdFilter, genreIdFilter, pageable)).thenReturn(content)

        assertEquals(content, service.find(bookNameFilter, authorIdFilter, genreIdFilter, page, size))

        verify(repository).find(bookNameFilter, authorIdFilter, genreIdFilter, pageable)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun get() {
        val author = Author(1, "Author1")
        val genre = Genre(1, "Genre1")

        val bookId = 1
        val book = Book(bookId, "Book1", "Book1 Original Name", 111, listOf(author), listOf(genre))

        whenever(repository.findById(bookId)).thenReturn(Optional.of(book))

        assertEquals(book, service.get(bookId))

        verify(repository).findById(bookId)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun gettingShouldThrowExceptionBecauseBookIsNotExists() {
        val bookId = 100500

        whenever(repository.findById(bookId)).thenReturn(Optional.empty())

        Assertions.assertThatThrownBy { service.get(bookId) }
            .isInstanceOf(IllegalArgumentException::class.java)

        verify(repository).findById(bookId)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun create() {
        val author = Author(1, "Author1")
        val genre = Genre(1, "Genre1")

        val book = Book(1, "Book1", "Book1 Original Name", 111, listOf(author), listOf(genre))
        val bookRequest = book.copy(id = null)

        whenever(repository.save(bookRequest)).thenReturn(book)

        assertEquals(book, service.create(bookRequest))

        verify(repository).save(bookRequest)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun update() {
        val author = Author(1, "Author1")
        val genre = Genre(1, "Genre1")

        val bookId = 1
        val book = Book(bookId, "Book1", "Book1 Original Name", 111, listOf(author), listOf(genre))

        whenever(repository.existsById(bookId)).thenReturn(true)
        whenever(repository.save(book)).thenReturn(book)

        assertEquals(book, service.update(book))

        verify(repository).existsById(bookId)
        verify(repository).save(book)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun updatingShouldThrowExceptionBecauseBookIdIsNull() {
        val book = Book(name = "Book1")

        Assertions.assertThatThrownBy { service.update(book) }
            .isInstanceOf(NullPointerException::class.java)

        verifyNoMoreInteractions(repository)
    }

    @Test
    fun updatingShouldThrowExceptionBecauseBookIsNotExists() {
        val bookId = 100500
        val book = Book(bookId, "Book100500")

        whenever(repository.existsById(bookId)).thenReturn(false)

        Assertions.assertThatThrownBy { service.update(book) }
            .isInstanceOf(IllegalArgumentException::class.java)

        verify(repository).existsById(bookId)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun delete() {
        val bookId = 1

        doNothing().whenever(repository).deleteById(bookId)

        service.delete(bookId)

        verify(repository).deleteById(bookId)
        verifyNoMoreInteractions(repository)
    }
}