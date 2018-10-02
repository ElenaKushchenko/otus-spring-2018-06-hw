package ru.otus.spring.kushchenko.hw8.service

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import ru.otus.spring.kushchenko.hw8.model.Book
import ru.otus.spring.kushchenko.hw8.model.User
import ru.otus.spring.kushchenko.hw8.repository.BookRepository
import ru.otus.spring.kushchenko.hw8.repository.UserRepository
import java.util.*

class LibraryServiceImplTest {
    private val bookRepository: BookRepository = mock()
    private val userRepository: UserRepository = mock()

    private val service = LibraryServiceImpl(bookRepository, userRepository)

    @Nested
    @DisplayName("Tests for takeBook() method")
    inner class TakeBook {

        @Test
        fun shouldThrowExceptionBecauseBookDoesNotExist() {
            val bookId = "100500"
            val userId = "1"

            whenever(bookRepository.findById(bookId)).thenReturn(Optional.empty())

            assertThatThrownBy { service.takeBook(bookId, userId) }
                .isInstanceOf(IllegalArgumentException::class.java)

            verify(bookRepository).findById(bookId)
            verifyNoMoreInteractions(bookRepository)
            verifyNoMoreInteractions(userRepository)
        }

        @Test
        fun shouldThrowExceptionBecauseBookIsAlreadyTaken() {
            val bookId = "1"
            val userId = "1"

            val book = Book(bookId, "Book1", user = User("1", "User1"))

            whenever(bookRepository.findById(bookId)).thenReturn(Optional.of(book))

            assertThatThrownBy { service.takeBook(bookId, userId) }
                .isInstanceOf(IllegalStateException::class.java)

            verify(bookRepository).findById(bookId)
            verifyNoMoreInteractions(bookRepository)
            verifyNoMoreInteractions(userRepository)
        }

        @Test
        fun shouldThrowExceptionBecauseUserDoesNotExist() {
            val bookId = "1"
            val userId = "100500"

            val book = Book(bookId, "Book1")

            whenever(bookRepository.findById(bookId)).thenReturn(Optional.of(book))
            whenever(userRepository.findById(userId)).thenReturn(Optional.empty())

            assertThatThrownBy { service.takeBook(bookId, userId) }
                .isInstanceOf(IllegalArgumentException::class.java)

            verify(bookRepository).findById(bookId)
            verify(userRepository).findById(userId)
            verifyNoMoreInteractions(bookRepository)
            verifyNoMoreInteractions(userRepository)
        }

        @Test
        fun shouldBeCompletedSuccessfully() {
            val bookId = "1"
            val userId = "1"

            val book = Book(bookId, "Book1")
            val user = User(userId, "User1")
            val updatedBook = book.copy(user = user)

            whenever(bookRepository.findById(bookId)).thenReturn(Optional.of(book))
            whenever(userRepository.findById(userId)).thenReturn(Optional.of(user))
            whenever(bookRepository.save(updatedBook)).thenReturn(updatedBook)

            service.takeBook(bookId, userId)

            verify(bookRepository).findById(bookId)
            verify(userRepository).findById(userId)
            verify(bookRepository).save(updatedBook)
            verifyNoMoreInteractions(bookRepository)
            verifyNoMoreInteractions(userRepository)
        }
    }

    @Nested
    @DisplayName("Tests for returnBook() method")
    inner class ReturnBook {

        @Test
        fun shouldThrowExceptionBecauseBookDoesNotExist() {
            val bookId = "100500"
            val userId = "1"

            whenever(bookRepository.findById(bookId)).thenReturn(Optional.empty())

            assertThatThrownBy { service.returnBook(bookId, userId) }
                .isInstanceOf(IllegalArgumentException::class.java)

            verify(bookRepository).findById(bookId)
            verifyNoMoreInteractions(bookRepository)
            verifyNoMoreInteractions(userRepository)
        }

        @Test
        fun shouldThrowExceptionBecauseBookIsAlreadyFree() {
            val bookId = "1"
            val userId = "1"

            val book = Book(bookId, "Book1")

            whenever(bookRepository.findById(bookId)).thenReturn(Optional.of(book))

            assertThatThrownBy { service.returnBook(bookId, userId) }
                .isInstanceOf(IllegalStateException::class.java)

            verify(bookRepository).findById(bookId)
            verifyNoMoreInteractions(bookRepository)
            verifyNoMoreInteractions(userRepository)
        }

        @Test
        fun shouldThrowExceptionBecauseUserDoesNotExist() {
            val bookId = "1"
            val userId = "100500"

            val book = Book(bookId, "Book1", user = User("1", "User1"))

            whenever(bookRepository.findById(bookId)).thenReturn(Optional.of(book))
            whenever(userRepository.findById(userId)).thenReturn(Optional.empty())

            assertThatThrownBy { service.returnBook(bookId, userId) }
                .isInstanceOf(IllegalArgumentException::class.java)

            verify(bookRepository).findById(bookId)
            verify(userRepository).findById(userId)
            verifyNoMoreInteractions(bookRepository)
            verifyNoMoreInteractions(userRepository)
        }

        @Test
        fun shouldBeCompletedSuccessfully() {
            val bookId = "1"
            val userId = "1"

            val user = User(userId, "User1")
            val book = Book(bookId, "Book1", user = user)
            val updatedBook = book.copy(user = null)

            whenever(bookRepository.findById(bookId)).thenReturn(Optional.of(book))
            whenever(userRepository.findById(userId)).thenReturn(Optional.of(user))
            whenever(bookRepository.save(updatedBook)).thenReturn(updatedBook)

            service.returnBook(bookId, userId)

            verify(bookRepository).findById(bookId)
            verify(userRepository).findById(userId)
            verify(bookRepository).save(updatedBook)
            verifyNoMoreInteractions(bookRepository)
            verifyNoMoreInteractions(userRepository)
        }
    }
}