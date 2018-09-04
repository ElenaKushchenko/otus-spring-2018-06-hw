package ru.otus.spring.kushchenko.hw6.repository

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.otus.spring.kushchenko.hw6.entity.Author
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

/**
 * Created by Елена on Июль, 2018
 */
@Repository
class AuthorRepositoryImpl : AuthorRepository {
    private val log = LoggerFactory.getLogger(AuthorRepositoryImpl::class.java)

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun getAll(): List<Author> {
        log.debug("Getting all authors")

        val query = "SELECT a FROM Author a"
        return entityManager.createQuery(query, Author::class.java)
            .resultList
    }

    override fun get(id: Int): Author {
        log.debug("Getting author with id = $id")

        return entityManager.find(Author::class.java, id)
    }

    @Transactional
    override fun create(author: Author): Author {
        log.debug("Inserting a new author: $author")

        entityManager.persist(author)
        return author
    }

    @Transactional
    override fun update(id: Int, author: Author): Author {
        log.debug("Updating author with id = ${author.id}")

        val toUpdate = entityManager.find(Author::class.java, id)
        toUpdate?.let {
            it.name = author.name

            entityManager.flush()
        }

        return toUpdate
    }

    @Transactional
    override fun delete(id: Int) {
        log.debug("Deleting author with id = $id")

        val toDelete = entityManager.find(Author::class.java, id)
        toDelete?.let { entityManager.remove(it) }
    }
}