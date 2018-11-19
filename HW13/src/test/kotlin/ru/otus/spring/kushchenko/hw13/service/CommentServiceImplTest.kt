package ru.otus.spring.kushchenko.hw13.service

import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit.jupiter.SpringExtension
import ru.otus.spring.kushchenko.hw13.model.Book
import ru.otus.spring.kushchenko.hw13.model.Comment
import ru.otus.spring.kushchenko.hw13.repository.BookRepository
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest
class CommentServiceImplTest {
    @MockBean
    private lateinit var bookRepository: BookRepository
    @Autowired
    private lateinit var service: CommentService

    @AfterEach
    fun clean() {
        reset(bookRepository)
    }

    @Nested
    @DisplayName("Tests for create() method")
    inner class Create {

        @Test
        @WithMockUser(authorities = ["USER"])
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
        @WithMockUser(authorities = ["USER"])
        fun shouldFailBecauseBookDoesNotExist() {
            val bookId = ""

            val comment = Comment(username = "User1", text = "Comment1")

            whenever(bookRepository.findById(bookId)).thenReturn(Optional.empty())

            Assertions.assertThatThrownBy { service.create(bookId, comment) }
                .isInstanceOf(IllegalArgumentException::class.java)

            verify(bookRepository).findById(bookId)
            verifyNoMoreInteractions(bookRepository)
        }

        @Test
        @WithMockUser(authorities = ["Admin"])
        fun shouldFailBecauseNoPermissions() {
            val bookId = "1"

            val comment = Comment(username = "User1", text = "Comment1")

            Assertions.assertThatThrownBy { service.create(bookId, comment) }
                .isInstanceOf(AccessDeniedException::class.java)

            verifyNoMoreInteractions(bookRepository)
        }
    }
}