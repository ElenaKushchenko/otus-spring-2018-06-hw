package ru.otus.spring.kushchenko.hw6.repository

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.InvalidDataAccessApiUsageException
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional
import ru.otus.spring.kushchenko.hw6.entity.Author
import javax.persistence.EntityManager

/**
 * Created by Елена on Авг., 2018
 */
@Transactional
@ExtendWith(SpringExtension::class)
@SpringBootTest
class AuthorRepositoryImplTest {
    @Autowired
    private lateinit var repository: AuthorRepositoryImpl
    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    fun getAll() {
        val author1 = insert(Author(name = "Author1"))
        val author2 = insert(Author(name = "Author1"))

        val actual = repository.getAll()
        assertIterableEquals(listOf(author1, author2), actual)
    }

    @Test
    fun get() {
        val author = insert(Author(name = "Author1"))
        val authorId = author.id!!

        val actual = repository.get(authorId)
        assertEquals(author, actual)
    }

    @Test
    fun gettingShouldFailWhenAuthorIsNotExists() {
        val authorId = 100500

        assertThatThrownBy { repository.get(authorId) }
            .isInstanceOf(InvalidDataAccessApiUsageException::class.java)
    }

    @Test
    fun create() {
        val author = Author(name = "Author1")

        val actual = repository.create(author)
        assertEquals(entityManager.find(Author::class.java, actual.id), actual)
    }

    @Test
    fun update() {
        val originalAuthor = insert(Author(name = "Author1"))

        val authorId = originalAuthor.id!!
        val author = originalAuthor.copy(name = "New Author1 Name")

        val actual = repository.update(authorId, author)
        assertEquals(entityManager.find(Author::class.java, authorId), actual)
    }

    @Test
    fun updatingShouldFailWhenAuthorIsNotExists() {
        val authorId = 100500
        val author = Author(name = "New Author100500 Name")

        assertThatThrownBy { repository.update(authorId, author) }
            .isInstanceOf(InvalidDataAccessApiUsageException::class.java)
    }

    @Test
    fun delete() {
        val author = insert(Author(name = "Author1"))
        val authorId = author.id!!

        repository.delete(authorId)
        assertNull(entityManager.find(Author::class.java, authorId))
    }

    private fun insert(author: Author) =
        author.also {
            entityManager.persist(author)
        }
}