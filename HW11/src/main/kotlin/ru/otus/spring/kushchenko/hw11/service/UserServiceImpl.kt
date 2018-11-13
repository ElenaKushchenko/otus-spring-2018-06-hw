package ru.otus.spring.kushchenko.hw11.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.otus.spring.kushchenko.hw11.model.User
import ru.otus.spring.kushchenko.hw11.repository.UserRepository
import java.lang.IllegalArgumentException

/**
 * Created by Елена on Июль, 2018
 */
@Service
class UserServiceImpl(private val repository: UserRepository) : UserService {
    override fun getAll(): Flux<User> = repository.findAll()

    override fun get(id: String): Mono<User> = repository.findById(id)
        .doOnNext { exists ->
            exists ?: throw IllegalArgumentException("User with id = $id not found")
        }

    override fun create(user: User): Mono<User> {
//        user.id?.let {
//            if (repository.existsById(it))
//                throw IllegalArgumentException("User with id = $it already exists")
//        }

        return repository.save(user)
    }

    override fun update(user: User): Mono<User> {
        val id = user.id!!
//
//        if (repository.existsById(id).not())
//            throw IllegalArgumentException("User with id = $id not found")

        return repository.save(user)
    }

    override fun delete(id: String) = repository.deleteById(id)
}