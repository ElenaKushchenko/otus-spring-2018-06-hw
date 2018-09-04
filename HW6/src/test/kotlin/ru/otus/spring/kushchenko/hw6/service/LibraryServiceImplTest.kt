package ru.otus.spring.kushchenko.hw6.service

import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Test
import ru.otus.spring.kushchenko.hw6.repository.LibraryDao

/**
 * Created by Елена on Авг., 2018
 */
internal class LibraryServiceImplTest {
    private val dao: LibraryDao = mock()
    private val service = LibraryServiceImpl(dao)

    @Test
    fun takeBook() {
        val bookId = 1
        val userId = 1

        doNothing().whenever(dao).takeBook(bookId, userId)

        service.takeBook(bookId, userId)

        verify(dao).takeBook(bookId, userId)
        verifyNoMoreInteractions(dao)
    }

    @Test
    fun returnBook() {
        val bookId = 1
        val userId = 1

        doNothing().whenever(dao).returnBook(bookId, userId)

        service.returnBook(bookId, userId)

        verify(dao).returnBook(bookId, userId)
        verifyNoMoreInteractions(dao)
    }
}