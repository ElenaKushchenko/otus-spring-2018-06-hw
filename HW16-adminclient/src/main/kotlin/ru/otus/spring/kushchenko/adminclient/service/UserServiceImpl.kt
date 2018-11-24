package ru.otus.spring.kushchenko.adminclient.service

import org.springframework.stereotype.Service
import ru.otus.spring.kushchenko.adminclient.model.User
import ru.otus.spring.kushchenko.adminclient.repository.UserRepository
import java.lang.IllegalArgumentException

/**
 * Created by Елена on Июль, 2018
 */
@Service
class UserServiceImpl(private val repository: UserRepository) : UserService {
    override fun getAll(): List<User> = repository.findAll()

    override fun get(id: String): User = repository.findById(id)
        .orElseThrow { IllegalArgumentException("User with id = $id not found") }

    override fun create(user: User): User {
        user.id?.let {
            if (repository.existsById(it))
                throw IllegalArgumentException("User with id = $it already exists")
        }

        return repository.save(user)
    }

    override fun update(user: User): User {
        val id = user.id!!

        if (repository.existsById(id).not())
            throw IllegalArgumentException("User with id = $id not found")

        return repository.save(user)
    }

    override fun delete(id: String) = repository.deleteById(id)
}