package ru.otus.spring.kushchenko.hw18.service

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.springframework.stereotype.Service
import ru.otus.spring.kushchenko.hw18.model.User
import ru.otus.spring.kushchenko.hw18.repository.UserRepository
import java.lang.IllegalArgumentException

/**
 * Created by Елена on Июль, 2018
 */
@Service
class UserServiceImpl(private val repository: UserRepository) : UserService {
    @HystrixCommand(groupKey = "User", commandKey = "GetAll")
    override fun getAll(): List<User> = repository.findAll()

    @HystrixCommand(groupKey = "User", commandKey = "Get")
    override fun get(id: String): User = repository.findById(id)
        .orElseThrow { IllegalArgumentException("User with id = $id not found") }

    @HystrixCommand(groupKey = "User", commandKey = "Create")
    override fun create(user: User): User {
        user.id?.let {
            if (repository.existsById(it))
                throw IllegalArgumentException("User with id = $it already exists")
        }

        return repository.save(user)
    }

    @HystrixCommand(groupKey = "User", commandKey = "Update")
    override fun update(user: User): User {
        val id = user.id!!

        if (repository.existsById(id).not())
            throw IllegalArgumentException("User with id = $id not found")

        return repository.save(user)
    }

    @HystrixCommand(groupKey = "User", commandKey = "Delete")
    override fun delete(id: String) = repository.deleteById(id)
}