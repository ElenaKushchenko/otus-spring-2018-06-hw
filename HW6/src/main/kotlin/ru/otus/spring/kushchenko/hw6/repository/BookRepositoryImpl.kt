package ru.otus.spring.kushchenko.hw6.repository

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.otus.spring.kushchenko.hw6.entity.Book
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

/**
 * Created by Елена on Июль, 2018
 */
@Repository
class BookRepositoryImpl: BookRepository {
    private val log = LoggerFactory.getLogger(BookRepositoryImpl::class.java)

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun getAll(): List<Book> {
        log.debug("Getting all books")

        val query = "SELECT b FROM Book b"
        return entityManager.createQuery(query, Book::class.java)
            .resultList
    }

    override fun get(id: Int): Book {
        log.debug("Getting book with id = $id")

        return entityManager.find(Book::class.java, id)
    }

    @Transactional
    override fun create(book: Book): Book {
        log.debug("Inserting a new book: $book")

        entityManager.persist(book)
        entityManager.flush()
        entityManager.refresh(book)

        return book
    }

    @Transactional
    override fun update(id: Int, book: Book): Book {
        log.debug("Updating book with id = ${book.id}")

        val toUpdate = entityManager.find(Book::class.java, id)
        toUpdate?.let {
            it.name = book.name
            it.originalName = book.originalName
            it.paperback = book.paperback
            it.authors = book.authors
            it.genres = book.genres

            entityManager.flush()
            entityManager.refresh(it)
        }

        return toUpdate
    }

    @Transactional
    override fun delete(id: Int) {
        log.debug("Deleting book with id = $id")

        val toDelete = entityManager.find(Book::class.java, id)
        toDelete?.let { entityManager.remove(it) }
    }
}