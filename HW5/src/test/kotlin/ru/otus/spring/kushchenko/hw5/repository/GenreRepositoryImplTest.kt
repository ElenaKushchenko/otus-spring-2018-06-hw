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
import ru.otus.spring.kushchenko.hw5.model.Genre

/**
 * Created by Елена on Авг., 2018
 */
@SpringBootTest
@ExtendWith(SpringExtension::class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = ["classpath:/data.sql"])
class GenreRepositoryImplTest {

    @TestConfiguration
    class GenreRepositoryTestConfiguration {

        @Bean
        fun genreRepositoryImpl(jdbcTemplate: NamedParameterJdbcTemplate) =
            GenreRepositoryImpl(jdbcTemplate)
    }

    @Autowired
    private lateinit var genreRepository: GenreRepository

    @Test
    fun getAll() {
        val expected = listOf(
            Genre(1, "Genre1"),
            Genre(2, "Genre2"),
            Genre(3, "Genre3")
        )

        assertEquals(expected, genreRepository.getAll())
    }

    @Test
    fun shouldGetExistingGenre() {
        val expectedId = 1
        val expected = Genre(expectedId, "Genre1")

        assertEquals(expected, genreRepository.get(expectedId))
    }

    @Test
    fun shouldGetNullBecauseGenreNotExists() {
        val expectedId = 100500

        assertNull(genreRepository.get(expectedId))
    }

    @Test
    fun create() {
        val expected = Genre(4, "Genre1")

        assertEquals(expected, genreRepository.create(expected))
    }

    @Test
    fun shouldUpdateExistingGenre() {
        val expectedId = 1
        val expected = Genre(expectedId, "Genre1 new name")

        assertEquals(expected, genreRepository.update(expectedId, expected))
    }

    @Test
    fun shouldThrowExceptionBecauseGenreNotExists() {
        val expectedId = 100500
        val expected = Genre(expectedId, "Genre100500 new name")

        assertThrows(DataIntegrityViolationException::class.java) { genreRepository.update(expectedId, expected) }
    }

    @Test
    fun delete() {
        val expectedId = 1

        genreRepository.delete(expectedId)
        assertNull(genreRepository.get(expectedId))
    }

    @Test
    fun findByBookId() {
        val bookId = 1
        val expected = listOf(
            Genre(1, "Genre1"),
            Genre(2, "Genre2")
        )

        assertEquals(expected, genreRepository.findByBookId(bookId))
    }
}