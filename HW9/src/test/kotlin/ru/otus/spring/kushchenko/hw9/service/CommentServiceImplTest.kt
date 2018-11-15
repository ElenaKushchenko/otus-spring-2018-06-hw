package ru.otus.spring.kushchenko.hw9.service

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import ru.otus.spring.kushchenko.hw9.model.Book
import ru.otus.spring.kushchenko.hw9.model.Comment
import ru.otus.spring.kushchenko.hw9.repository.BookRepository
import java.util.*

class CommentServiceImplTest {
    private val bookRepository: BookRepository = mock()
    private val service = CommentServiceImpl(bookRepository)

    @Nested
    @DisplayName("Tests for create() method")
    inner class Create {

        @Test
        fun shouldPassSuccessfully() {
            val bookId = "1"
            val book = Book(bookId, "Book1")

            val comment = Comment(username = "User1", text = "Comment1")

            whenever(bookRepository.findById(bookId)).thenReturn(Optional.of(book))

            assertEquals(comment, service.create(bookId, comment))

            verify(bookRepository).findById(bookId)
            verify(bookRepository).save(book.copy(comments = listOf(comment)))
            verifyNoMoreInteractions(bookRepository)
        }

        @Test
        fun shouldFailBecauseBookDoesNotExist() {
            val bookId = ""

            val comment = Comment(username = "User1", text = "Comment1")

            whenever(bookRepository.findById(bookId)).thenReturn(Optional.empty())

            Assertions.assertThatThrownBy { service.create(bookId, comment) }
                .isInstanceOf(IllegalArgumentException::class.java)

            verify(bookRepository).findById(bookId)
            verifyNoMoreInteractions(bookRepository)
        }
    }
}