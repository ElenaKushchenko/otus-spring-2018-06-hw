package ru.otus.spring.kushchenko.hw6.service

import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.otus.spring.kushchenko.hw6.entity.Comment
import ru.otus.spring.kushchenko.hw6.entity.User
import ru.otus.spring.kushchenko.hw6.repository.CommentRepository
import java.time.LocalDateTime

/**
 * Created by Елена on Авг., 2018
 */
class CommentServiceImplTest {
    private val repository: CommentRepository = mock()
    private val service = CommentServiceImpl(repository)

    @Test
    fun getAll() {
        val user1 = User(1, "User1")
        val user2 = User(2, "User2")

        val comments = listOf(
            Comment(1, "Comment1", LocalDateTime.now(), user1, 1),
            Comment(2, "Comment2", LocalDateTime.now(), user2, 2)
        )

        whenever(repository.getAll()).thenReturn(comments)

        assertEquals(comments, service.getAll())

        verify(repository).getAll()
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun get() {
        val user = User(1, "User1")

        val commentId = 1
        val comment = Comment(commentId, "Comment1", LocalDateTime.now(), user, 1)


        whenever(repository.get(commentId)).thenReturn(comment)

        assertEquals(comment, service.get(commentId))

        verify(repository).get(commentId)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun create() {
        val user = User(1, "User1")

        val comment = Comment(1, "Comment1", LocalDateTime.now(), user, 1)
        val commentRequest = comment.copy(id = null)

        whenever(repository.create(commentRequest)).thenReturn(comment)

        assertEquals(comment, service.create(commentRequest))

        verify(repository).create(commentRequest)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun update() {
        val user = User(1, "User1")

        val commentId = 1
        val comment = Comment(commentId, "Comment1", LocalDateTime.now(), user, 1)
        val commentRequest = comment.copy(id = null)

        whenever(repository.update(commentId, commentRequest)).thenReturn(comment)

        assertEquals(comment, service.update(commentId, commentRequest))

        verify(repository).update(commentId, commentRequest)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun delete() {
        val commentId = 1

        doNothing().whenever(repository).delete(commentId)

        service.delete(commentId)

        verify(repository).delete(commentId)
        verifyNoMoreInteractions(repository)
    }
}