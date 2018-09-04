package ru.otus.spring.kushchenko.hw6.service

import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.otus.spring.kushchenko.hw6.entity.Author
import ru.otus.spring.kushchenko.hw6.repository.AuthorRepository

/**
 * Created by Елена on Авг., 2018
 */
class AuthorServiceImplTest {
    private val repository: AuthorRepository = mock()
    private val service = AuthorServiceImpl(repository)

    @Test
    fun getAll() {
        val authors = listOf(
            Author(1, "Author1"),
            Author(2, "Author2")
        )

        whenever(repository.getAll()).thenReturn(authors)

        assertEquals(authors, service.getAll())

        verify(repository).getAll()
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun get() {
        val authorId = 1
        val author = Author(authorId, "Author1")

        whenever(repository.get(authorId)).thenReturn(author)

        assertEquals(author, service.get(authorId))

        verify(repository).get(authorId)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun create() {
        val author = Author(1, "Author1")
        val authorRequest = author.copy(id = null)

        whenever(repository.create(authorRequest)).thenReturn(author)

        assertEquals(author, service.create(authorRequest))

        verify(repository).create(authorRequest)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun update() {
        val authorId = 1
        val author = Author(authorId, "Author1")
        val authorRequest = author.copy(id = null)

        whenever(repository.update(authorId, authorRequest)).thenReturn(author)

        assertEquals(author, service.update(authorId, authorRequest))

        verify(repository).update(authorId, authorRequest)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun delete() {
        val authorId = 1

        doNothing().whenever(repository).delete(authorId)

        service.delete(authorId)

        verify(repository).delete(authorId)
        verifyNoMoreInteractions(repository)
    }
}