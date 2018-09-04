package ru.otus.spring.kushchenko.hw6.repository

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.InvalidDataAccessApiUsageException
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional
import ru.otus.spring.kushchenko.hw6.entity.Author
import ru.otus.spring.kushchenko.hw6.entity.Book
import ru.otus.spring.kushchenko.hw6.entity.Genre
import ru.otus.spring.kushchenko.hw6.entity.User
import javax.persistence.EntityManager

/**
 * Created by Елена on Авг., 2018
 */
@Transactional
@ExtendWith(SpringExtension::class)
@SpringBootTest
class BookRepositoryImplTest {
    @Autowired
    private lateinit var repository: BookRepositoryImpl
    @Autowired
    private lateinit var entityManager: EntityManager

    private lateinit var author: Author
    private lateinit var genre: Genre
    private lateinit var user: User

    @BeforeEach
    fun setup() {
        author = Author(name = "Author1").also { entityManager.persist(it) }
        genre = Genre(name = "Genre1").also { entityManager.persist(it) }
        user = User(name = "User1").also { entityManager.persist(it) }
    }

    @Test
    fun getAll() {
        val book1 = insert(Book(name = "Book1", user = user, authors = listOf(author), genres = listOf(genre)))
        val book2 = insert(Book(name = "Book2", user = user, authors = listOf(author), genres = listOf(genre)))

        val actual = repository.getAll()
        assertIterableEquals(listOf(book1, book2), actual)
    }

    @Test
    fun get() {
        val book = insert(Book(name = "Book1", user = user, authors = listOf(author), genres = listOf(genre)))
        val bookId = book.id!!

        val actual = repository.get(bookId)
        assertEquals(book, actual)
    }

    @Test
    fun gettingShouldFailWhenBookIsNotExists() {
        val bookId = 100500

        Assertions.assertThatThrownBy { repository.get(bookId) }
            .isInstanceOf(InvalidDataAccessApiUsageException::class.java)
    }

    @Test
    fun create() {
        val book = insert(Book(name = "Book1", user = user, authors = listOf(author), genres = listOf(genre)))

        val actual = repository.create(book)
        assertEquals(entityManager.find(Book::class.java, actual.id), actual)
    }

    @Test
    fun update() {
        val originalBook = insert(Book(name = "Book1", user = user, authors = listOf(author), genres = listOf(genre)))

        val bookId = originalBook.id!!
        val book = originalBook.copy(name = "New Book1 Name")

        val actual = repository.update(bookId, book)
        assertEquals(entityManager.find(Book::class.java, bookId), actual)
    }

    @Test
    fun updatingShouldFailWhenBookIsNotExists() {
        val bookId = 100500
        val book = Book(name = "Book1", user = user, authors = listOf(author), genres = listOf(genre))

        Assertions.assertThatThrownBy { repository.update(bookId, book) }
            .isInstanceOf(InvalidDataAccessApiUsageException::class.java)
    }

    @Test
    fun delete() {
        val book = insert(Book(name = "Book1", user = user, authors = listOf(author), genres = listOf(genre)))
        val bookId = book.id!!

        repository.delete(bookId)
        assertNull(entityManager.find(Book::class.java, bookId))
    }

    private fun insert(book: Book) =
        book.also {
            entityManager.persist(it)
        }
}