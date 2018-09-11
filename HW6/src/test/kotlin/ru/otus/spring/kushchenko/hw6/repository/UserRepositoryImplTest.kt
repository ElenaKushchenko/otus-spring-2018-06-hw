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
import ru.otus.spring.kushchenko.hw6.entity.User
import javax.persistence.EntityManager

/**
 * Created by Елена on Авг., 2018
 */
@Transactional
@ExtendWith(SpringExtension::class)
@SpringBootTest
class UserRepositoryImplTest {
    @Autowired
    private lateinit var repository: UserRepositoryImpl
    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    fun getAll() {
        val user1 = insert(User(name = "User1"))
        val user2 = insert(User(name = "User1"))

        val actual = repository.getAll()
        assertIterableEquals(listOf(user1, user2), actual)
    }

    @Test
    fun get() {
        val user = insert(User(name = "User1"))
        val userId = user.id!!

        val actual = repository.get(userId)
        assertEquals(user, actual)
    }

    @Test
    fun gettingShouldFailWhenUserIsNotExists() {
        val userId = 100500

        Assertions.assertThatThrownBy { repository.get(userId) }
            .isInstanceOf(InvalidDataAccessApiUsageException::class.java)
    }

    @Test
    fun create() {
        val user = User(name = "User1")

        val actual = repository.create(user)
        assertEquals(entityManager.find(User::class.java, actual.id), actual)
    }

    @Test
    fun update() {
        val originalUser = insert(User(name = "User1"))

        val userId = originalUser.id!!
        val user = originalUser.copy(name = "New User1 Name")

        val actual = repository.update(userId, user)
        assertEquals(entityManager.find(User::class.java, userId), actual)
    }

    @Test
    fun updatingShouldFailWhenUserIsNotExists() {
        val userId = 100500
        val user = User(name = "New User100500 Name")

        Assertions.assertThatThrownBy { repository.update(userId, user) }
            .isInstanceOf(InvalidDataAccessApiUsageException::class.java)
    }

    @Test
    fun delete() {
        val user = insert(User(name = "User1"))
        val userId = user.id!!

        repository.delete(userId)
        assertNull(entityManager.find(User::class.java, userId))
    }

    private fun insert(user: User) =
        user.also {
            entityManager.persist(it)
        }
}