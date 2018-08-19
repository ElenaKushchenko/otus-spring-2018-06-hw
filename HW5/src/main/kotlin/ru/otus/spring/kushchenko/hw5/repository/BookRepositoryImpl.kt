package ru.otus.spring.kushchenko.hw5.repository

import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.otus.spring.kushchenko.hw5.model.Book

/**
 * Created by Елена on Июль, 2018
 */
@Repository
class BookRepositoryImpl(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
    private val authorRepository: AuthorRepository,
    private val genreRepository: GenreRepository,
    private val readerRepository: ReaderRepository
) : BookRepository {
    private val log = LoggerFactory.getLogger(BookRepositoryImpl::class.java)

    private val bookMapper = RowMapper { rs, _ ->
        val book = Book(
            id = rs.getInt("Id"),
            name = rs.getString("Name"),
            originalName = rs.getString("OriginalName"),
            paperback = rs.getInt("Paperback")
        )

        book.authors = authorRepository.findByBookId(book.id)
        book.genres = genreRepository.findByBookId(book.id)
        book.reader = readerRepository.get(rs.getInt("ReaderId"))

        book
    }

    override fun getAll(): List<Book> {
        log.debug("Getting all books")

        val sql = """
            select Id, Name, OriginalName, Paperback, ReaderId
            from Book
        """

        return jdbcTemplate.query(sql, bookMapper)
    }

    override fun get(id: Int): Book? {
        log.debug("Getting book with id = $id")

        val sql = """
            select Id, Name, OriginalName, Paperback, ReaderId
            from Book
            where Id = :id
        """

        return try {
            jdbcTemplate.queryForObject(sql, mapOf("id" to id), bookMapper)
        } catch (ex: EmptyResultDataAccessException) {
            log.info("Book with id = $id not found")
            null
        }
    }

    @Transactional
    override fun create(book: Book): Book {
        val sql = """
            insert into Book (Name, OriginalName, Paperback) values (:name, :originalName, :paperback)
        """

        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(sql, BeanPropertySqlParameterSource(book), keyHolder)
        val bookId = keyHolder.key!!.toInt()

        book.authors?.let { authors ->
            val bookAuthorSql = """
                insert into BookAuthor (BookId, AuthorId) values (:bookId, :authorId)
            """

            val params = authors.map { mapOf("bookId" to bookId, "authorId" to it.id) }.toTypedArray()
            jdbcTemplate.batchUpdate(bookAuthorSql, params)
        }
        book.genres?.let { genres ->
            val bookGenreSql = """
                insert into BookGenre (BookId, GenreId) values (:bookId, :genreId)
            """

            val params = genres.map { mapOf("bookId" to bookId, "genreId" to it.id) }.toTypedArray()
            jdbcTemplate.batchUpdate(bookGenreSql, params)
        }

        return get(bookId)!!
    }

    @Transactional
    override fun update(id: Int, book: Book): Book {
        log.debug("Updating book with id = ${book.id}")

        val sql = """
            update Book set Name = :name, OriginalName = :originalName, Paperback = :paperback where Id = :id
        """

        val params = mapOf(
            "id" to id,
            "name" to book.name,
            "originalName" to book.originalName,
            "paperback" to book.paperback
        )
        if (jdbcTemplate.update(sql, params) == 0) {
            throw DataIntegrityViolationException("Book with id = $id not found")
        }

        book.authors?.let { authors ->
            val bookAuthorDelete = """
                delete from BookAuthor where BookId = :bookId
            """
            jdbcTemplate.update(bookAuthorDelete, mapOf("bookId" to id))

            val bookAuthorInsert = """
                insert into BookAuthor (BookId, AuthorId) values (:bookId, :authorId)
            """

            val params = authors.map { mapOf("bookId" to id, "authorId" to it.id) }.toTypedArray()
            jdbcTemplate.batchUpdate(bookAuthorInsert, params)
        }
        book.genres?.let { genres ->
            val bookGenreDelete = """
                delete from BookGenre where BookId = :bookId
            """
            jdbcTemplate.update(bookGenreDelete, mapOf("bookId" to id))

            val bookGenreInsert = """
                insert into BookGenre (BookId, GenreId) values (:bookId, :genreId)
            """

            val params = genres.map { mapOf("bookId" to id, "genreId" to it.id) }.toTypedArray()
            jdbcTemplate.batchUpdate(bookGenreInsert, params)
        }

        return get(id)!!
    }

    override fun delete(id: Int) {
        log.debug("Deleting book with id = $id")

        val sql = """
            delete from Book where Id = :id
        """

        if (jdbcTemplate.update(sql, mapOf("id" to id)) == 0)
            log.error("Book with id = $id not found")
    }
}