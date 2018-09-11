package ru.otus.spring.kushchenko.hw6.repository

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.InvalidDataAccessApiUsageException
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional
import ru.otus.spring.kushchenko.hw6.entity.Book
import ru.otus.spring.kushchenko.hw6.entity.Comment
import ru.otus.spring.kushchenko.hw6.entity.User
import javax.persistence.EntityManager

/**
 * Created by Елена on Авг., 2018
 */
@Transactional
@ExtendWith(SpringExtension::class)
@SpringBootTest
class CommentRepositoryImplTest {
    @Autowired
    private lateinit var repository: CommentRepositoryImpl
    @Autowired
    private lateinit var entityManager: EntityManager

    private lateinit var book: Book
    private lateinit var user: User

    @BeforeEach
    fun setup() {
        book = Book(name = "Book1").also { entityManager.persist(it) }
        user = User(name = "User1").also { entityManager.persist(it) }
    }

    @Test
    fun getAll() {
        val comment1 = insert(Comment(text = "Comment1", user = user, bookId = book.id!!))
        val comment2 = insert(Comment(text = "Comment2", user = user, bookId = book.id!!))

        val actual = repository.getAll()
        assertIterableEquals(listOf(comment1, comment2), actual)
    }

    @Test
    fun get() {
        val comment = insert(Comment(text = "Comment1", user = user, bookId = book.id!!))
        val commentId = comment.id!!

        val actual = repository.get(commentId)
        assertEquals(comment, actual)
    }

    @Test
    fun gettingShouldFailWhenCommentIsNotExists() {
        val commentId = 100500

        Assertions.assertThatThrownBy { repository.get(commentId) }
            .isInstanceOf(InvalidDataAccessApiUsageException::class.java)
    }

    @Test
    fun create() {
        val comment = insert(Comment(text = "Comment1", user = user, bookId = book.id!!))

        val actual = repository.create(comment)
        assertEquals(entityManager.find(Comment::class.java, actual.id), actual)
    }

    @Test
    fun update() {
        val originalComment = insert(Comment(text = "Comment1", user = user, bookId = book.id!!))

        val commentId = originalComment.id!!
        val comment = originalComment.copy(text = "New Comment1 Name")

        val actual = repository.update(commentId, comment)
        assertEquals(entityManager.find(Comment::class.java, commentId), actual)
    }

    @Test
    fun updatingShouldFailWhenCommentIsNotExists() {
        val commentId = 100500
        val comment = Comment(text = "Comment1", user = user, bookId = book.id!!)

        Assertions.assertThatThrownBy { repository.update(commentId, comment) }
            .isInstanceOf(InvalidDataAccessApiUsageException::class.java)
    }

    @Test
    fun delete() {
        val comment = insert(Comment(text = "Comment1", user = user, bookId = book.id!!))
        val commentId = comment.id!!

        repository.delete(commentId)
        assertNull(entityManager.find(Comment::class.java, commentId))
    }

    private fun insert(comment: Comment) =
        comment.also {
            entityManager.persist(it)
        }
}