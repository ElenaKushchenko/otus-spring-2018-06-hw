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
import ru.otus.spring.kushchenko.hw5.model.Author
import ru.otus.spring.kushchenko.hw5.model.Book
import ru.otus.spring.kushchenko.hw5.model.Genre
import ru.otus.spring.kushchenko.hw5.model.Reader

/**
 * Created by Елена on Авг., 2018
 */
@SpringBootTest
@ExtendWith(SpringExtension::class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = ["classpath:/data.sql"])
class BookRepositoryImplTest {

    @TestConfiguration
    class BookRepositoryImplTestConfiguration {

        @Bean
        fun authorRepositoryImpl(jdbcTemplate: NamedParameterJdbcTemplate) =
            AuthorRepositoryImpl(jdbcTemplate)

        @Bean
        fun genreRepositoryImpl(jdbcTemplate: NamedParameterJdbcTemplate) =
            GenreRepositoryImpl(jdbcTemplate)

        @Bean
        fun readerRepositoryImpl(jdbcTemplate: NamedParameterJdbcTemplate) =
            ReaderRepositoryImpl(jdbcTemplate)

        @Bean
        fun bookRepositoryImpl(jdbcTemplate: NamedParameterJdbcTemplate,
                               authorRepository: AuthorRepository,
                               genreRepository: GenreRepository,
                               readerRepository: ReaderRepository) =
            BookRepositoryImpl(jdbcTemplate, authorRepository, genreRepository, readerRepository)
    }

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Test
    fun getAll() {
        val author1 = Author(1, "Author1")
        val author2 = Author(2, "Author2")
        val author3 = Author(3, "Author3")
        val genre1 = Genre(1, "Genre1")
        val genre2 = Genre(2, "Genre2")
        val genre3 = Genre(3, "Genre3")
        val reader1 = Reader(1, "Reader1")

        val expected = listOf(
            Book(1, "Book1", "Book1 Original Name", 111, listOf(author1, author2), listOf(genre1, genre2), reader1),
            Book(2, "Book2", "Book2 Original Name", 222, listOf(author2, author3), listOf(genre2, genre3))
        )

        assertEquals(expected, bookRepository.getAll())
    }

    @Test
    fun shouldGetExistingBook() {
        val author1 = Author(1, "Author1")
        val author2 = Author(2, "Author2")
        val genre1 = Genre(1, "Genre1")
        val genre2 = Genre(2, "Genre2")
        val reader1 = Reader(1, "Reader1")

        val expectedId = 1
        val expected = Book(expectedId, "Book1", "Book1 Original Name", 111, listOf(author1, author2), listOf(genre1, genre2), reader1)

        assertEquals(expected, bookRepository.get(expectedId))
    }

    @Test
    fun shouldGetNullBecauseBookNotExists() {
        val expectedId = 100500

        assertNull(bookRepository.get(expectedId))
    }

    @Test
    fun create() {
        val author = Author(1, "Author1")
        val genre = Genre(1, "Genre1")

        val expectedId = 3
        val expected = Book(expectedId, "Book3", "Book3 Original Name", 333, listOf(author), listOf(genre))

        assertEquals(expected, bookRepository.create(expected))
    }

    @Test
    fun shouldUpdateExistingBook() {
        val author = Author(1, "Author1")
        val genre = Genre(1, "Genre1")
        val reader = Reader(1, "Reader1")

        val expectedId = 1
        val expected = Book(expectedId, "Book1", "Book1 Original Name", 111, listOf(author), listOf(genre), reader)

        assertEquals(expected, bookRepository.update(expectedId, expected))
    }

    @Test
    fun shouldThrowExceptionBecauseBookNotExists() {
        val author = Author(1, "Author1")
        val genre = Genre(1, "Genre1")
        val reader = Reader(1, "Reader1")

        val expectedId = 100500
        val expected = Book(expectedId, "Book100500", "Book100500 Original Name", 100500, listOf(author), listOf(genre), reader)

        assertThrows(DataIntegrityViolationException::class.java) { bookRepository.update(expectedId, expected) }
    }


    @Test
    fun delete() {
        val expectedId = 1

        bookRepository.delete(expectedId)
        assertNull(bookRepository.get(expectedId))
    }
}