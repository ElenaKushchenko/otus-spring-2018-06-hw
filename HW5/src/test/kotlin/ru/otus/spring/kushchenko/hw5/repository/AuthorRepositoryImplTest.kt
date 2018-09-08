package ru.otus.spring.kushchenko.hw5.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit.jupiter.SpringExtension
import ru.otus.spring.kushchenko.hw5.model.Author

/**
 * Created by Елена on Авг., 2018
 */
@SpringBootTest
@ExtendWith(SpringExtension::class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = ["classpath:/data.sql"])
class AuthorRepositoryImplTest {

    @TestConfiguration
    class AuthorRepositoryTestConfiguration {

        @Bean
        fun authorRepositoryImpl(jdbcTemplate: NamedParameterJdbcTemplate) =
            AuthorRepositoryImpl(jdbcTemplate)
    }

    @Autowired
    private lateinit var authorRepository: AuthorRepository

    @Test
    fun getAll() {
        val expected = listOf(
            Author(1, "Author1"),
            Author(2, "Author2"),
            Author(3, "Author3")
        )

        assertEquals(expected, authorRepository.getAll())
    }

    @Test
    fun shouldGetExistingAuthor() {
        val expectedId = 1
        val expected = Author(expectedId, "Author1")

        assertEquals(expected, authorRepository.get(expectedId))
    }

    @Test
    fun shouldGetNullBecauseAuthorNotExists() {
        val expectedId = 100500

        assertNull(authorRepository.get(expectedId))
    }

    @Test
    fun create() {
        val expected = Author(4, "Author1")

        assertEquals(expected, authorRepository.create(expected))
    }

    @Test
    fun shouldUpdateExistingAuthor() {
        val expectedId = 1
        val expected = Author(expectedId, "Author1 new name")

        assertEquals(expected, authorRepository.update(expectedId, expected))
    }

    @Test
    fun shouldThrowExceptionBecauseAuthorNotExists() {
        val expectedId = 100500
        val expected = Author(expectedId, "Author100500 new name")

        assertThrows(DataIntegrityViolationException::class.java) { authorRepository.update(expectedId, expected) }
    }

    @Test
    fun delete() {
        val expectedId = 1

        authorRepository.delete(expectedId)
        assertNull(authorRepository.get(expectedId))
    }

    @Test
    fun findByBookId() {
        val bookId = 1
        val expected = listOf(
            Author(1, "Author1"),
            Author(2, "Author2")
        )

        assertEquals(expected, authorRepository.findByBookId(bookId))
    }
}