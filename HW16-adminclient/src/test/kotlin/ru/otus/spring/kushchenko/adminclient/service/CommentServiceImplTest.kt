package ru.otus.spring.kushchenko.adminclient.service

import com.nhaarman.mockito_kotlin.*
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import ru.otus.spring.kushchenko.adminclient.model.Book
import ru.otus.spring.kushchenko.adminclient.model.Comment
import ru.otus.spring.kushchenko.adminclient.model.User
import ru.otus.spring.kushchenko.adminclient.repository.BookRepository
import ru.otus.spring.kushchenko.adminclient.repository.CommentRepository
import ru.otus.spring.kushchenko.adminclient.repository.UserRepository
import java.util.*

class CommentServiceImplTest {
    private val commentRepository: CommentRepository = mock()
    private val bookRepository: BookRepository = mock()
    private val userRepository: UserRepository = mock()
    private val mongoTemplate: MongoTemplate = mock()
    private val service = CommentServiceImpl(commentRepository, userRepository, bookRepository, mongoTemplate)

    @Test
    fun getAll() {
        val user1 = User("1", "User1")
        val user2 = User("2", "User2")
        val book1 = Book("1", "Book1")
        val book2 = Book("2", "Book2")

        val comments = listOf(
            Comment(id = "1", text = "Comment1", user = user1, book = book1),
            Comment(id = "2", text = "Comment2", user = user2, book = book2),
            Comment(id = "3", text = "Comment3", user = user1, book = book2),
            Comment(id = "4", text = "Comment4", user = user2, book = book1)
        )

        whenever(commentRepository.findAll()).thenReturn(comments)

        assertEquals(comments, service.getAll())

        verify(commentRepository).findAll()
        verifyZeroInteractions(commentRepository, bookRepository, userRepository, mongoTemplate)
    }

    @Test
    fun getPaged() {
        val userId = "1"
        val bookId = "1"
        val user = User(userId, "User1")
        val book = Book(bookId, "Book1")

        val comments = listOf(
            Comment(id = "1", text = "Comment1", user = user, book = book),
            Comment(id = "2", text = "Comment2", user = user, book = book),
            Comment(id = "3", text = "Comment3", user = user, book = book),
            Comment(id = "4", text = "Comment4", user = user, book = book)
        )

        val page = 1
        val size = 20
        val sortBy = "name"
        val dir = "ASC"

        val pageable = PageRequest.of(
            page - 1,
            size,
            Sort(Sort.Direction.valueOf(dir), sortBy)
        )

        val commentsPage = PageImpl(comments, pageable, comments.size.toLong())

        val query = Query()
            .addCriteria(Criteria.where("user.id").`is`(userId))
            .addCriteria(Criteria.where("book.id").`is`(bookId))
            .with(pageable)

        whenever(mongoTemplate.count(query, Comment::class.java)).thenReturn(comments.size.toLong())
        whenever(mongoTemplate.find(query, Comment::class.java)).thenReturn(comments)

        assertEquals(commentsPage, service.getFiltered(userId, bookId, page, size, sortBy, dir))

        verify(mongoTemplate).count(query, Comment::class.java)
        verify(mongoTemplate).find(query, Comment::class.java)
        verifyZeroInteractions(commentRepository, bookRepository, userRepository, mongoTemplate)
    }

    @Nested
    @DisplayName("Tests for get() method")
    inner class Get {

        @Test
        fun shouldPassSuccessfully() {
            val user = User("1", "User1")
            val book = Book("1", "Book1")

            val commentId = "1"
            val comment = Comment(id = commentId, text = "Comment1", user = user, book = book)

            whenever(commentRepository.findById(commentId)).thenReturn(Optional.of(comment))

            assertEquals(comment, service.get(commentId))

            verify(commentRepository).findById(commentId)
            verifyZeroInteractions(commentRepository, bookRepository, userRepository, mongoTemplate)
        }

        @Test
        fun shouldFailBecauseCommentDoesNotExist() {
            val commentId = "100500"

            whenever(commentRepository.findById(commentId)).thenReturn(Optional.empty())

            Assertions.assertThatThrownBy { service.get(commentId) }
                .isInstanceOf(IllegalArgumentException::class.java)

            verify(commentRepository).findById(commentId)
            verifyZeroInteractions(commentRepository, bookRepository, userRepository, mongoTemplate)
        }
    }

    @Nested
    @DisplayName("Tests for create() method")
    inner class Create {

        @Test
        fun shouldPassSuccessfully() {
            val userId = "1"
            val bookId = "1"
            val user = User(userId, "User1")
            val book = Book(bookId, "Book1")

            val commentId = "1"
            val comment = Comment(id = commentId, text = "Comment1", user = user, book = book)

            whenever(commentRepository.existsById(commentId)).thenReturn(false)
            whenever(userRepository.findById(userId)).thenReturn(Optional.of(user))
            whenever(bookRepository.findById(bookId)).thenReturn(Optional.of(book))
            whenever(commentRepository.save(comment)).thenReturn(comment)

            assertEquals(comment, service.create(comment))

            verify(commentRepository).existsById(commentId)
            verify(userRepository).findById(userId)
            verify(bookRepository).findById(bookId)
            verify(commentRepository).save(comment)
            verifyZeroInteractions(commentRepository, bookRepository, userRepository, mongoTemplate)
        }

        @Test
        fun shouldFailBecauseCommentAlreadyExists() {
            val user = User("1", "User1")
            val book = Book("1", "Book1")

            val commentId = "1"
            val comment = Comment(id = commentId, text = "Comment1", user = user, book = book)

            whenever(commentRepository.existsById(commentId)).thenReturn(true)

            Assertions.assertThatThrownBy { service.create(comment) }
                .isInstanceOf(IllegalArgumentException::class.java)

            verify(commentRepository).existsById(commentId)
            verifyZeroInteractions(commentRepository, bookRepository, userRepository, mongoTemplate)
        }

        @Test
        fun shouldFailBecauseUserDoesNotExist() {
            val userId = "1"
            val user = User(userId, "User1")
            val book = Book("1", "Book1")

            val commentId = "1"
            val comment = Comment(id = commentId, text = "Comment1", user = user, book = book)

            whenever(commentRepository.existsById(commentId)).thenReturn(false)
            whenever(userRepository.findById(userId)).thenReturn(Optional.empty())

            Assertions.assertThatThrownBy { service.create(comment) }
                .isInstanceOf(IllegalArgumentException::class.java)

            verify(commentRepository).existsById(commentId)
            verify(userRepository).findById(userId)
            verifyZeroInteractions(commentRepository, bookRepository, userRepository, mongoTemplate)
        }

        @Test
        fun shouldFailBecauseBookDoesNotExist() {
            val userId = "1"
            val bookId = ""
            val user = User(userId, "User1")
            val book = Book(bookId, "Book1")

            val commentId = "1"
            val comment = Comment(id = commentId, text = "Comment1", user = user, book = book)

            whenever(commentRepository.existsById(commentId)).thenReturn(false)
            whenever(userRepository.findById(userId)).thenReturn(Optional.of(user))
            whenever(bookRepository.findById(bookId)).thenReturn(Optional.empty())

            Assertions.assertThatThrownBy { service.create(comment) }
                .isInstanceOf(IllegalArgumentException::class.java)

            verify(commentRepository).existsById(commentId)
            verify(userRepository).findById(userId)
            verify(bookRepository).findById(bookId)
            verifyZeroInteractions(commentRepository, bookRepository, userRepository, mongoTemplate)
        }
    }

    @Nested
    @DisplayName("Tests for update() method")
    inner class Update {

        @Test
        fun shouldPassSuccessfully() {
            val userId = "1"
            val bookId = "1"
            val user = User(userId, "User1")
            val book = Book(bookId, "Book1")

            val commentId = "1"
            val comment = Comment(id = commentId, text = "Comment1", user = user, book = book)

            whenever(commentRepository.existsById(commentId)).thenReturn(true)
            whenever(userRepository.findById(userId)).thenReturn(Optional.of(user))
            whenever(bookRepository.findById(bookId)).thenReturn(Optional.of(book))
            whenever(commentRepository.save(comment)).thenReturn(comment)

            assertEquals(comment, service.update(comment))

            verify(commentRepository).existsById(commentId)
            verify(userRepository).findById(userId)
            verify(bookRepository).findById(bookId)
            verify(commentRepository).save(comment)
            verifyZeroInteractions(commentRepository, bookRepository, userRepository, mongoTemplate)
        }

        @Test
        fun shouldFailBecauseCommentDoesNotExist() {
            val user = User("1", "User1")
            val book = Book("1", "Book1")

            val commentId = "1"
            val comment = Comment(id = commentId, text = "Comment1", user = user, book = book)

            whenever(commentRepository.existsById(commentId)).thenReturn(false)

            Assertions.assertThatThrownBy { service.update(comment) }
                .isInstanceOf(IllegalArgumentException::class.java)

            verify(commentRepository).existsById(commentId)
            verifyZeroInteractions(commentRepository, bookRepository, userRepository, mongoTemplate)
        }

        @Test
        fun shouldFailBecauseUserDoesNotExist() {
            val userId = "1"
            val user = User(userId, "User1")
            val book = Book("1", "Book1")

            val commentId = "1"
            val comment = Comment(id = commentId, text = "Comment1", user = user, book = book)

            whenever(commentRepository.existsById(commentId)).thenReturn(true)
            whenever(userRepository.findById(userId)).thenReturn(Optional.empty())

            Assertions.assertThatThrownBy { service.update(comment) }
                .isInstanceOf(IllegalArgumentException::class.java)

            verify(commentRepository).existsById(commentId)
            verify(userRepository).findById(userId)
            verifyZeroInteractions(commentRepository, bookRepository, userRepository, mongoTemplate)
        }

        @Test
        fun shouldFailBecauseBookDoesNotExist() {
            val userId = "1"
            val bookId = ""
            val user = User(userId, "User1")
            val book = Book(bookId, "Book1")

            val commentId = "1"
            val comment = Comment(id = commentId, text = "Comment1", user = user, book = book)

            whenever(commentRepository.existsById(commentId)).thenReturn(true)
            whenever(userRepository.findById(userId)).thenReturn(Optional.of(user))
            whenever(bookRepository.findById(bookId)).thenReturn(Optional.empty())

            Assertions.assertThatThrownBy { service.update(comment) }
                .isInstanceOf(IllegalArgumentException::class.java)

            verify(commentRepository).existsById(commentId)
            verify(userRepository).findById(userId)
            verify(bookRepository).findById(bookId)
            verifyZeroInteractions(commentRepository, bookRepository, userRepository, mongoTemplate)
        }

        @Test
        fun shouldFailBecauseCommentIdNotSpecified() {
            val user = User("1", "User1")
            val book = Book("1", "Book1")

            val comment = Comment(id = null, text = "Comment1", user = user, book = book)

            Assertions.assertThatThrownBy { service.update(comment) }
                .isInstanceOf(NullPointerException::class.java)

            verifyZeroInteractions(commentRepository, bookRepository, userRepository, mongoTemplate)
        }
    }

    @Test
    fun delete() {
        val commentId = "1"

        doNothing().whenever(commentRepository).deleteById(commentId)

        service.delete(commentId)

        verify(commentRepository).deleteById(commentId)
        verifyZeroInteractions(commentRepository, bookRepository, userRepository, mongoTemplate)
    }
}