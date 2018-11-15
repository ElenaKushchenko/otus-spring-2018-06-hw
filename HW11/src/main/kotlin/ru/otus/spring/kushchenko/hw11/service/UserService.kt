package ru.otus.spring.kushchenko.hw11.service

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.otus.spring.kushchenko.hw11.model.User

/**
 * Created by Елена on Июль, 2018
 */
interface UserService {
    fun getAll(): Flux<User>
    fun get(id: String): Mono<User>
    fun create(user: User): Mono<User>
    fun update(user: User): Mono<User>
    fun delete(id: String): Mono<Void>
}