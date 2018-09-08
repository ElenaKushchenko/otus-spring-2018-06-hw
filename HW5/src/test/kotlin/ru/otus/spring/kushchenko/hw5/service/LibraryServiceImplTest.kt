package ru.otus.spring.kushchenko.hw5.service

import com.nhaarman.mockito_kotlin.doNothing
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Test
import ru.otus.spring.kushchenko.hw5.repository.LibraryDao

/**
 * Created by Елена on Авг., 2018
 */
internal class LibraryServiceImplTest {
    private val dao: LibraryDao = mock()
    private val service = LibraryServiceImpl(dao)

    @Test
    fun takeBook() {
        val bookId = 1
        val readerId = 1

        doNothing().whenever(dao).takeBook(bookId, readerId)

        service.takeBook(bookId, readerId)

        verify(dao).takeBook(bookId, readerId)
        verifyNoMoreInteractions(dao)
    }

    @Test
    fun returnBook() {
        val bookId = 1
        val readerId = 1

        doNothing().whenever(dao).returnBook(bookId, readerId)

        service.returnBook(bookId, readerId)

        verify(dao).returnBook(bookId, readerId)
        verifyNoMoreInteractions(dao)
    }
}