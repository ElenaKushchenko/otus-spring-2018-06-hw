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
        .switchIfEmpty(Mono.error(IllegalArgumentException("User with id = $id not found")))

    override fun create(user: User): Mono<User> =
        Mono.just(user.id != null)
            .filter { presented -> presented }
            .flatMap {
                repository.existsById(user.id!!)
                    .filter { exists -> exists }
                    .flatMap { Mono.error<User>(IllegalArgumentException("User with id = ${user.id} already exists")) }
            }
            .switchIfEmpty(repository.save(user))

    override fun update(user: User): Mono<User> =
        repository.existsById(user.id!!)
            .flatMap { exists ->
                if (exists) repository.save(user)
                else Mono.error(IllegalArgumentException("User with id = ${user.id} not found"))
            }

    override fun delete(id: String) = repository.deleteById(id)
}