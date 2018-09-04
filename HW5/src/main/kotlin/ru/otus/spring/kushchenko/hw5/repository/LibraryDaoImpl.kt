package ru.otus.spring.kushchenko.hw5.repository

import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component

/**
 * Created by Елена on Июль, 2018
 */
@Component
class LibraryDaoImpl(private val jdbcTemplate: NamedParameterJdbcTemplate) : LibraryDao {
    private val log = LoggerFactory.getLogger(LibraryDaoImpl::class.java)

    override fun takeBook(bookId: Int, readerId: Int) {
        val sql = """
            update Book set ReaderId = :readerId where ReaderId is null and Id = :bookId
        """

        if (jdbcTemplate.update(sql, mapOf("readerId" to readerId, "bookId" to bookId)) == 0)
            throw throw DataIntegrityViolationException("Failed to take book: book ID = $bookId, reader ID = $readerId")
    }

    override fun returnBook(bookId: Int, readerId: Int) {
        val sql = """
            update Book set ReaderId = null where ReaderId = :readerId and Id = :bookId
        """

        if (jdbcTemplate.update(sql, mapOf("readerId" to readerId, "bookId" to bookId)) == 0)
            throw throw DataIntegrityViolationException("Failed to return book: book ID = $bookId, reader ID = $readerId")
    }
}