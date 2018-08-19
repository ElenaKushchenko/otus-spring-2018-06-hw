package ru.otus.spring.kushchenko.hw5.repository

import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import ru.otus.spring.kushchenko.hw5.model.Genre

/**
 * Created by Елена on Июль, 2018
 */
@Repository
class GenreRepositoryImpl(private val jdbcTemplate: NamedParameterJdbcTemplate) : GenreRepository {
    private val log = LoggerFactory.getLogger(GenreRepositoryImpl::class.java)

    private val genreMapper = RowMapper { rs, _ ->
        Genre(id = rs.getInt("Id"), name = rs.getString("Name"))
    }

    override fun getAll(): List<Genre> {
        log.debug("Getting all genres")

        val sql = """
            select Id, Name
            from Genre
        """

        return jdbcTemplate.query(sql, genreMapper)
    }

    override fun get(id: Int): Genre? {
        log.debug("Getting genre with id = $id")

        val sql = """
            select Id, Name
            from Genre
            where Id = :id
        """

        return try {
            jdbcTemplate.queryForObject(sql, mapOf("id" to id), genreMapper)
        } catch (ex: EmptyResultDataAccessException) {
            log.error("Genre with id = $id not found")
            null
        }
    }

    override fun create(genre: Genre): Genre {
        log.debug("Inserting a new genre with name = ${genre.name}")

        val sql = """
            insert into Genre (Name) values (:name)
        """

        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(sql, BeanPropertySqlParameterSource(genre), keyHolder)
        return genre.copy(id = keyHolder.key!!.toInt())
    }

    override fun update(id: Int, genre: Genre): Genre {
        log.debug("Updating genre with id = ${genre.id}")

        val sql = """
            update Genre set Name = :name where Id = :id
        """

        if (jdbcTemplate.update(sql, mapOf("id" to id, "name" to genre.name)) == 0) {
            throw DataIntegrityViolationException("Genre with id = $id not found")
        } else {
            return genre.copy(id = id)
        }
    }

    override fun delete(id: Int) {
        log.debug("Deleting genre with id = $id")

        val sql = """
            delete from Genre where Id = :id
        """

        if (jdbcTemplate.update(sql, mapOf("id" to id)) == 0)
            log.error("Genre with id = $id not found")
    }

    override fun findByBookId(bookId: Int): List<Genre> {
        log.debug("Getting genres by book id = $bookId")

        val sql = """
            select g.Id, g.Name
            from Genre g
                inner join BookGenre bg
                on g.Id = bg.genreId
            where bg.bookId = :bookId
        """

        return jdbcTemplate.query(sql, mapOf("bookId" to bookId), genreMapper)
    }
}