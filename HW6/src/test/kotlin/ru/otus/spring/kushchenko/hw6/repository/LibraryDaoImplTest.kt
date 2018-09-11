package ru.otus.spring.kushchenko.hw6.repository

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import ru.otus.spring.kushchenko.hw6.entity.Book
import ru.otus.spring.kushchenko.hw6.entity.User

/**
 * Created by Елена on Авг., 2018
 */
class LibraryDaoImplTest {
    private val bookRepository: BookRepository = mock()
    private val userRepository: UserRepository = mock()

    private val libraryDao = LibraryDaoImpl(bookRepository, userRepository)

    @Nested
    @DisplayName("Tests for takeBook method")
    inner class TakeBook {
        @Test
        fun shouldPassSuccessfully() {
            val bookId = 1
            val userId = 1
            val book = Book(id = bookId, name = "Book1")
            val user = User(id = userId, name = "User1")

            val takenBook = book.copy(user = user)

            whenever(bookRepository.get(bookId)).thenReturn(book)
            whenever(userRepository.get(userId)).thenReturn(user)
            whenever(bookRepository.update(bookId, takenBook)).thenReturn(takenBook)

            libraryDao.takeBook(bookId, userId)

            verify(bookRepository).get(bookId)
            verify(userRepository).get(userId)
            verify(bookRepository).update(bookId, takenBook)
            verifyNoMoreInteractions(bookRepository)
            verifyNoMoreInteractions(userRepository)
        }

        @Test
        fun shouldFallWhenBookIsAlreadyTaken() {
            val userId = 1
            val bookId = 1
            val user = User(id = 1, name = "User1")
            val book = Book(id = bookId, name = "Book1", user = user)

            whenever(bookRepository.get(bookId)).thenReturn(book)

            Assertions.assertThatThrownBy { libraryDao.takeBook(bookId, userId) }
                .isInstanceOf(IllegalStateException::class.java)

            verify(bookRepository).get(bookId)
            verifyNoMoreInteractions(bookRepository)
            verifyNoMoreInteractions(userRepository)
        }
    }

    @Nested
    @DisplayName("Tests for returnBook method")
    inner class ReturnBook {
        @Test
        fun shouldPassSuccessfully() {
            val userId = 1
            val bookId = 1
            val user = User(id = userId, name = "User1")
            val book = Book(id = bookId, name = "Book1", user = user)

            val freeBook = book.copy(user = null)

            whenever(bookRepository.get(bookId)).thenReturn(book)
            whenever(userRepository.get(userId)).thenReturn(user)
            whenever(bookRepository.update(bookId, freeBook)).thenReturn(freeBook)

            libraryDao.returnBook(bookId, userId)

            verify(bookRepository).get(bookId)
            verify(userRepository).get(userId)
            verify(bookRepository).update(bookId, freeBook)
            verifyNoMoreInteractions(bookRepository)
            verifyNoMoreInteractions(userRepository)
        }

        @Test
        fun shouldFallWhenBookIsAlreadyFree() {
            val userId = 1
            val bookId = 1
            val book = Book(id = bookId, name = "Book1")

            whenever(bookRepository.get(bookId)).thenReturn(book)

            Assertions.assertThatThrownBy { libraryDao.returnBook(bookId, userId) }
                .isInstanceOf(IllegalStateException::class.java)

            verify(bookRepository).get(bookId)
            verifyNoMoreInteractions(bookRepository)
            verifyNoMoreInteractions(userRepository)
        }
    }
}