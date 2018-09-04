package ru.otus.spring.kushchenko.hw6.repository

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.otus.spring.kushchenko.hw6.entity.Genre
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

/**
 * Created by Елена on Июль, 2018
 */
@Repository
class GenreRepositoryImpl : GenreRepository {
    private val log = LoggerFactory.getLogger(GenreRepositoryImpl::class.java)

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun getAll(): List<Genre> {
        log.debug("Getting all genres")

        val query = "SELECT g FROM Genre g"
        return entityManager.createQuery(query, Genre::class.java)
            .resultList
    }

    override fun get(id: Int): Genre {
        log.debug("Getting genre with id = $id")

        return entityManager.find(Genre::class.java, id)
    }

    @Transactional
    override fun create(genre: Genre): Genre {
        log.debug("Inserting a new genre: $genre")

        entityManager.persist(genre)
        return genre
    }

    @Transactional
    override fun update(id: Int, genre: Genre): Genre {
        log.debug("Updating genre with id = ${genre.id}")

        val toUpdate = entityManager.find(Genre::class.java, id)
        toUpdate?.let {
            it.name = genre.name

            entityManager.flush()
        }

        return toUpdate
    }

    @Transactional
    override fun delete(id: Int) {
        log.debug("Deleting genre with id = $id")

        val toDelete = entityManager.find(Genre::class.java, id)
        toDelete?.let { entityManager.remove(toDelete) }
    }
}