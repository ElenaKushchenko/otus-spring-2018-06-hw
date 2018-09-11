package ru.otus.spring.kushchenko.hw6.repository

import org.assertj.core.api.Assertions
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
import ru.otus.spring.kushchenko.hw6.entity.Genre
import javax.persistence.EntityManager

/**
 * Created by Елена on Авг., 2018
 */
@Transactional
@ExtendWith(SpringExtension::class)
@SpringBootTest
class GenreRepositoryImplTest {
    @Autowired
    private lateinit var repository: GenreRepositoryImpl
    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    fun getAll() {
        val genre1 = insert(Genre(name = "Genre1"))
        val genre2 = insert(Genre(name = "Genre1"))

        val actual = repository.getAll()
        assertIterableEquals(listOf(genre1, genre2), actual)
    }

    @Test
    fun get() {
        val genre = insert(Genre(name = "Genre1"))
        val genreId = genre.id!!

        val actual = repository.get(genreId)
        assertEquals(genre, actual)
    }

    @Test
    fun gettingShouldFailWhenGenreIsNotExists() {
        val genreId = 100500

        Assertions.assertThatThrownBy { repository.get(genreId) }
            .isInstanceOf(InvalidDataAccessApiUsageException::class.java)
    }

    @Test
    fun create() {
        val genre = Genre(name = "Genre1")

        val actual = repository.create(genre)
        assertEquals(entityManager.find(Genre::class.java, actual.id), actual)
    }

    @Test
    fun update() {
        val originalGenre = insert(Genre(name = "Genre1"))

        val genreId = originalGenre.id!!
        val genre = originalGenre.copy(name = "New Genre1 Name")

        val actual = repository.update(genreId, genre)
        assertEquals(entityManager.find(Genre::class.java, genreId), actual)
    }

    @Test
    fun updatingShouldFailWhenGenreIsNotExists() {
        val genreId = 100500
        val genre = Genre(name = "New Genre100500 Name")

        Assertions.assertThatThrownBy { repository.update(genreId, genre) }
            .isInstanceOf(InvalidDataAccessApiUsageException::class.java)
    }

    @Test
    fun delete() {
        val genre = insert(Genre(name = "Genre1"))
        val genreId = genre.id!!

        repository.delete(genreId)
        assertNull(entityManager.find(Genre::class.java, genreId))
    }

    private fun insert(genre: Genre) =
        genre.also {
            entityManager.persist(it)
        }
}