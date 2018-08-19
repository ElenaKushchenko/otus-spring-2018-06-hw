package ru.otus.spring.kushchenko.hw5.repository

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * Created by Елена on Авг., 2018
 */
@SpringBootTest
@ExtendWith(SpringExtension::class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = ["classpath:/data.sql"])
class LibraryDaoImplTest {

    @TestConfiguration
    class LibraryDaoTestConfiguration {

        @Bean
        fun libraryDaoImpl(jdbcTemplate: NamedParameterJdbcTemplate) =
            LibraryDaoImpl(jdbcTemplate)
    }

    @Autowired
    private lateinit var libraryDao: LibraryDao

    @Test
    fun shouldTakeFreeBook() {
        val bookId = 2
        val readerId = 2

        assertDoesNotThrow { libraryDao.takeBook(bookId, readerId) }
    }

    @Test
    fun shouldThrowExceptionBecauseBookAlreadyTaken() {
        val bookId = 1
        val readerId = 2

        assertThrows(DataIntegrityViolationException::class.java) { libraryDao.takeBook(bookId, readerId) }
    }

    @Test
    fun shouldReturnTakenBook() {
        val bookId = 1
        val readerId = 1

        assertDoesNotThrow { libraryDao.returnBook(bookId, readerId) }
    }

    @Test
    fun shouldThrowExceptionBecauseBookAlreadyFree() {
        val bookId = 2
        val readerId = 2

        assertThrows(DataIntegrityViolationException::class.java) { libraryDao.returnBook(bookId, readerId) }
    }
}