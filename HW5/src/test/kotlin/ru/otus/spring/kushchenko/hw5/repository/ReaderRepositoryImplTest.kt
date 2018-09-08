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
import ru.otus.spring.kushchenko.hw5.model.Reader

/**
 * Created by Елена on Авг., 2018
 */
@SpringBootTest
@ExtendWith(SpringExtension::class)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = ["classpath:/data.sql"])
class ReaderRepositoryImplTest {

    @TestConfiguration
    class ReaderRepositoryTestConfiguration {

        @Bean
        fun readerRepositoryImpl(jdbcTemplate: NamedParameterJdbcTemplate) =
            ReaderRepositoryImpl(jdbcTemplate)
    }

    @Autowired
    private lateinit var readerRepository: ReaderRepository

    @Test
    fun getAll() {
        val expected = listOf(
            Reader(1, "Reader1"),
            Reader(2, "Reader2")
        )

        assertEquals(expected, readerRepository.getAll())
    }

    @Test
    fun shouldGetExistingReader() {
        val expectedId = 1
        val expected = Reader(expectedId, "Reader1")

        assertEquals(expected, readerRepository.get(expectedId))
    }

    @Test
    fun shouldGetNullBecauseReaderNotExists() {
        val expectedId = 100500

        assertNull(readerRepository.get(expectedId))
    }

    @Test
    fun create() {
        val expected = Reader(3, "Reader1")

        assertEquals(expected, readerRepository.create(expected))
    }

    @Test
    fun shouldUpdateExistingReader() {
        val expectedId = 1
        val expected = Reader(expectedId, "Reader1 new name")

        assertEquals(expected, readerRepository.update(expectedId, expected))
    }

    @Test
    fun shouldThrowExceptionBecauseReaderNotExists() {
        val expectedId = 100500
        val expected = Reader(expectedId, "Reader100500 new name")

        assertThrows(DataIntegrityViolationException::class.java) { readerRepository.update(expectedId, expected) }
    }

    @Test
    fun delete() {
        val expectedId = 1

        readerRepository.delete(expectedId)
        assertNull(readerRepository.get(expectedId))
    }
}