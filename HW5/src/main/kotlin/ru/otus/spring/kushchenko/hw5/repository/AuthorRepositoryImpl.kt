package ru.otus.spring.kushchenko.hw5.repository

import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import ru.otus.spring.kushchenko.hw5.model.Author

/**
 * Created by Елена on Июль, 2018
 */
@Repository
class AuthorRepositoryImpl(private val jdbcTemplate: NamedParameterJdbcTemplate) : AuthorRepository {
    private val log = LoggerFactory.getLogger(AuthorRepositoryImpl::class.java)

    private val authorMapper = RowMapper { rs, _ ->
        Author(id = rs.getInt("Id"), name = rs.getString("Name"))
    }

    override fun getAll(): List<Author> {
        log.debug("Getting all authors")

        val sql = """
            select Id, Name
            from Author
        """

        return jdbcTemplate.query(sql, authorMapper)
    }

    override fun get(id: Int): Author? {
        log.debug("Getting author with id = $id")

        val sql = """
            select Id, Name
            from Author
            where Id = :id
        """

        return try {
            jdbcTemplate.queryForObject(sql, mapOf("id" to id), authorMapper)
        } catch (ex: EmptyResultDataAccessException) {
            log.info("Author with id = $id not found")
            null
        }
    }

    override fun create(author: Author): Author {
        log.debug("Inserting a new author with name = ${author.name}")

        val sql = """
            insert into Author (Name) values (:name)
        """

        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(sql, BeanPropertySqlParameterSource(author), keyHolder)
        return author.copy(id = keyHolder.key!!.toInt())
    }

    override fun update(id: Int, author: Author): Author {
        log.debug("Updating author with id = ${author.id}")

        val sql = """
            update Author set Name = :name where Id = :id
        """

        if (jdbcTemplate.update(sql, mapOf("id" to id, "name" to author.name)) == 0) {
            throw DataIntegrityViolationException("Author with id = $id not found")
        } else {
            return author.copy(id = id)
        }
    }

    override fun delete(id: Int) {
        log.debug("Deleting author with id = $id")

        val sql = """
            delete from Author where Id = :id
        """

        if (jdbcTemplate.update(sql, mapOf("id" to id)) == 0)
            log.error("Author with id = $id not found")
    }

    override fun findByBookId(bookId: Int): List<Author> {
        log.debug("Getting authors by book id = $bookId")

        val sql = """
            select a.Id, a.Name
            from Author a
                inner join BookAuthor ba
                on a.Id = ba.authorId
            where ba.bookId = :bookId
        """

        return jdbcTemplate.query(sql, mapOf("bookId" to bookId), authorMapper)
    }
}