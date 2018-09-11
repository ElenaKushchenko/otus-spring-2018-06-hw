package ru.otus.spring.kushchenko.hw6.repository

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ru.otus.spring.kushchenko.hw6.entity.User
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

/**
 * Created by Елена on Июль, 2018
 */
@Repository
class UserRepositoryImpl : UserRepository {
    private val log = LoggerFactory.getLogger(UserRepositoryImpl::class.java)

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun getAll(): List<User> {
        log.debug("Getting all users")

        val query = "SELECT r FROM User r"
        return entityManager.createQuery(query, User::class.java)
            .resultList
    }

    override fun get(id: Int): User {
        log.debug("Getting user with id = $id")

        return entityManager.find(User::class.java, id)
    }

    @Transactional
    override fun create(user: User): User {
        log.debug("Inserting a new user: $user")

        entityManager.persist(user)
        return user
    }

    @Transactional
    override fun update(id: Int, user: User): User {
        log.debug("Updating user with id = ${user.id}")

        val toUpdate = entityManager.find(User::class.java, id)
        toUpdate?.let {
            it.name = user.name

            entityManager.flush()
        }

        return toUpdate
    }

    @Transactional
    override fun delete(id: Int) {
        log.debug("Deleting user with id = $id")

        val toDelete = entityManager.find(User::class.java, id)
        toDelete?.let { entityManager.remove(toDelete) }
    }
}