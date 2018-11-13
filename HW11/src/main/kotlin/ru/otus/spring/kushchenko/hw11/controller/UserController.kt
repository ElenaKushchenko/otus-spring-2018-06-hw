package ru.otus.spring.kushchenko.hw11.controller

import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.otus.spring.kushchenko.hw11.model.User
import ru.otus.spring.kushchenko.hw11.service.UserService

/**
 * Created by Елена on Июль, 2018
 */
@RestController
@RequestMapping("/users")
class UserController(private val service: UserService) {

    @GetMapping
    fun getAll(): Flux<User> =
        service.getAll()

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: String): Mono<User> =
        service.get(id)

    @PostMapping
    fun create(@RequestBody user: User): Mono<User> =
        service.create(user)

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: String,
               @RequestBody user: User): Mono<User> =
        service.update(user.copy(id = id))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: String) =
        service.delete(id)

}