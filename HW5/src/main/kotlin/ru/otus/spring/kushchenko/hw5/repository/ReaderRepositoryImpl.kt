package ru.otus.spring.kushchenko.hw5.repository

import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import ru.otus.spring.kushchenko.hw5.model.Reader

/**
 * Created by Елена on Июль, 2018
 */
@Repository
class ReaderRepositoryImpl(private val jdbcTemplate: NamedParameterJdbcTemplate) : ReaderRepository {
    private val log = LoggerFactory.getLogger(ReaderRepositoryImpl::class.java)

    private val readerMapper = RowMapper { rs, _ ->
        Reader(id = rs.getInt("Id"), name = rs.getString("Name"))
    }

    override fun getAll(): List<Reader> {
        log.debug("Getting all readers")

        val sql = """
            select Id, Name
            from Reader
        """

        return jdbcTemplate.query(sql, readerMapper)
    }

    override fun get(id: Int): Reader? {
        log.debug("Getting reader with id = $id")

        val sql = """
            select Id, Name
            from Reader
            where Id = :id
        """

        return try {
            jdbcTemplate.queryForObject(sql, mapOf("id" to id), readerMapper)
        } catch (ex: EmptyResultDataAccessException) {
            log.info("Reader with id = $id not found")
            null
        }
    }

    override fun create(reader: Reader): Reader {
        log.debug("Inserting a new reader with name = ${reader.name}")

        val sql = """
            insert into Reader (Name) values (:name)
        """

        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(sql, BeanPropertySqlParameterSource(reader), keyHolder)
        return reader.copy(id = keyHolder.key!!.toInt())
    }

    override fun update(id: Int, reader: Reader): Reader {
        log.debug("Updating reader with id = ${reader.id}")

        val sql = """
            update Reader set Name = :name where Id = :id
        """

        if (jdbcTemplate.update(sql, mapOf("id" to id, "name" to reader.name)) == 0) {
            throw DataIntegrityViolationException("Reader with id = $id not found")
        } else {
            return reader.copy(id = id)
        }
    }

    override fun delete(id: Int) {
        log.debug("Deleting reader with id = $id")

        val sql = """
            delete from Reader where Id = :id
        """

        if (jdbcTemplate.update(sql, mapOf("id" to id)) == 0)
            log.error("Reader with id = $id not found")
    }
}