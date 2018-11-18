package ru.otus.spring.kushchenko.hw12.service

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.otus.spring.kushchenko.hw12.repository.AuthorRepository

class AuthorServiceImplTest {
    private val repository: AuthorRepository = mock()
    private val service = AuthorServiceImpl(repository)

    @Test
    fun getAll() {
        val authors = listOf("Author1", "Author2")

        whenever(repository.getAll()).thenReturn(authors)

        assertEquals(authors, service.getAll())

        verify(repository).getAll()
        verifyNoMoreInteractions(repository)
    }
}