package ru.otus.spring.kushchenko.hw9.service

import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.otus.spring.kushchenko.hw9.model.Comment
import ru.otus.spring.kushchenko.hw9.repository.CommentRepository
import java.lang.IllegalArgumentException
import java.time.LocalDateTime
import java.util.*

/**
 * Created by Елена on Авг., 2018
 */
class CommentServiceImplTest {
    private val repository: CommentRepository = mock()
    private val service = CommentServiceImpl(repository)

    @Test
    fun getAllShortBooks() {
        val comments = listOf(
            Comment(1, "Comment1", LocalDateTime.now(), "UserName"),
            Comment(2, "Comment2", LocalDateTime.now(), "UserName")
        )

        whenever(repository.findAll()).thenReturn(comments)

        assertEquals(comments, service.getAll())

        verify(repository).findAll()
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun get() {
        val commentId = 1
        val comment = Comment(commentId, "Comment1", LocalDateTime.now(), "UserName")

        whenever(repository.findById(commentId)).thenReturn(Optional.of(comment))

        assertEquals(comment, service.get(commentId))

        verify(repository).findById(commentId)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun gettingShouldThrowExceptionBecauseCommentIsNotExists() {
        val commentId = 100500

        whenever(repository.findById(commentId)).thenReturn(Optional.empty())

        Assertions.assertThatThrownBy { service.get(commentId) }
            .isInstanceOf(IllegalArgumentException::class.java)

        verify(repository).findById(commentId)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun create() {
        val comment = Comment(1, "Comment1", LocalDateTime.now(), "UserName")
        val commentRequest = comment.copy(id = null)

        whenever(repository.save(commentRequest)).thenReturn(comment)

        assertEquals(comment, service.create(commentRequest))

        verify(repository).save(commentRequest)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun update() {
        val commentId = 1
        val comment = Comment(commentId, "Comment1", LocalDateTime.now(), "UserName")

        whenever(repository.existsById(commentId)).thenReturn(true)
        whenever(repository.save(comment)).thenReturn(comment)

        assertEquals(comment, service.update(comment))

        verify(repository).existsById(commentId)
        verify(repository).save(comment)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun updatingShouldThrowExceptionBecauseCommentIdIsNull() {
        val comment = Comment(text = "Comment1", userName = "UserName")

        Assertions.assertThatThrownBy { service.update(comment) }
            .isInstanceOf(NullPointerException::class.java)

        verifyNoMoreInteractions(repository)
    }

    @Test
    fun updatingShouldThrowExceptionBecauseCommentIsNotExists() {
        val commentId = 100500
        val comment = Comment(id = commentId, text = "Comment100500", userName = "UserName")

        whenever(repository.existsById(commentId)).thenReturn(false)

        Assertions.assertThatThrownBy { service.update(comment) }
            .isInstanceOf(IllegalArgumentException::class.java)

        verify(repository).existsById(commentId)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun delete() {
        val commentId = 1

        doNothing().whenever(repository).deleteById(commentId)

        service.delete(commentId)

        verify(repository).deleteById(commentId)
        verifyNoMoreInteractions(repository)
    }
}