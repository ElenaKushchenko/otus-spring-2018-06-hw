//package ru.otus.spring.kushchenko.hw11.service
//
//import com.nhaarman.mockito_kotlin.mock
//import com.nhaarman.mockito_kotlin.verify
//import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
//import com.nhaarman.mockito_kotlin.whenever
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.Test
//import ru.otus.spring.kushchenko.hw11.model.Author
//import ru.otus.spring.kushchenko.hw11.repository.AuthorRepository
//import java.time.LocalDate
//
//class AuthorServiceImplTest {
//    private val repository: AuthorRepository = mock()
//    private val service = AuthorServiceImpl(repository)
//
//    @Test
//    fun getAll() {
//        val authors = listOf(
//            Author("Author1", "USA", LocalDate.parse("1991-01-01")),
//            Author("Author2", "Russia", LocalDate.parse("1992-02-02"))
//        )
//
//        whenever(repository.getAll()).thenReturn(authors)
//
//        assertEquals(authors, service.getAll())
//
//        verify(repository).getAll()
//        verifyNoMoreInteractions(repository)
//    }
//}