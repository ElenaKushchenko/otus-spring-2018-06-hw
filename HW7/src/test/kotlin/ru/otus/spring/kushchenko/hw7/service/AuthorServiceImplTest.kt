//package ru.otus.spring.kushchenko.hw7.service
//
//import com.nhaarman.mockito_kotlin.doNothing
//import com.nhaarman.mockito_kotlin.mock
//import com.nhaarman.mockito_kotlin.verify
//import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
//import com.nhaarman.mockito_kotlin.whenever
//import org.assertj.core.api.Assertions.assertThatThrownBy
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.Test
//import ru.otus.spring.kushchenko.hw7.entity.Author
//import ru.otus.spring.kushchenko.hw7.repository.AuthorRepository
//import java.lang.IllegalArgumentException
//import java.util.*
//
///**
// * Created by Елена on Авг., 2018
// */
//class AuthorServiceImplTest {
//    private val repository: AuthorRepository = mock()
//    private val service = AuthorServiceImpl(repository)
//
//    @Test
//    fun getAllShortBooks() {
//        val authors = listOf(
//            Author(1, "Author1"),
//            Author(2, "Author2")
//        )
//
//        whenever(repository.findAll()).thenReturn(authors)
//
//        assertEquals(authors, service.getAllShortBooks())
//
//        verify(repository).findAll()
//        verifyNoMoreInteractions(repository)
//    }
//
//    @Test
//    fun get() {
//        val authorId = 1
//        val author = Author(authorId, "Author1")
//
//        whenever(repository.findById(authorId)).thenReturn(Optional.of(author))
//
//        assertEquals(author, service.get(authorId))
//
//        verify(repository).findById(authorId)
//        verifyNoMoreInteractions(repository)
//    }
//
//    @Test
//    fun gettingShouldThrowExceptionBecauseAuthorIsNotExists() {
//        val authorId = 100500
//
//        whenever(repository.findById(authorId)).thenReturn(Optional.empty())
//
//        assertThatThrownBy { service.get(authorId) }
//            .isInstanceOf(IllegalArgumentException::class.java)
//
//        verify(repository).findById(authorId)
//        verifyNoMoreInteractions(repository)
//    }
//
//    @Test
//    fun create() {
//        val author = Author(1, "Author1")
//        val authorRequest = author.copy(id = null)
//
//        whenever(repository.save(authorRequest)).thenReturn(author)
//
//        assertEquals(author, service.create(authorRequest))
//
//        verify(repository).save(authorRequest)
//        verifyNoMoreInteractions(repository)
//    }
//
//    @Test
//    fun update() {
//        val authorId = 1
//        val author = Author(authorId, "Author1")
//
//        whenever(repository.existsById(authorId)).thenReturn(true)
//        whenever(repository.save(author)).thenReturn(author)
//
//        assertEquals(author, service.update(author))
//
//        verify(repository).existsById(authorId)
//        verify(repository).save(author)
//        verifyNoMoreInteractions(repository)
//    }
//
//    @Test
//    fun updatingShouldThrowExceptionBecauseAuthorIdIsNull() {
//        val author = Author(name = "Author1")
//
//        assertThatThrownBy { service.update(author) }
//            .isInstanceOf(NullPointerException::class.java)
//
//        verifyNoMoreInteractions(repository)
//    }
//
//    @Test
//    fun updatingShouldThrowExceptionBecauseAuthorIsNotExists() {
//        val authorId = 100500
//        val author = Author(id = authorId, name = "Author100500")
//
//        whenever(repository.existsById(authorId)).thenReturn(false)
//
//        assertThatThrownBy { service.update(author) }
//            .isInstanceOf(IllegalArgumentException::class.java)
//
//        verify(repository).existsById(authorId)
//        verifyNoMoreInteractions(repository)
//    }
//
//    @Test
//    fun delete() {
//        val authorId = 1
//
//        doNothing().whenever(repository).deleteById(authorId)
//
//        service.delete(authorId)
//
//        verify(repository).deleteById(authorId)
//        verifyNoMoreInteractions(repository)
//    }
//}