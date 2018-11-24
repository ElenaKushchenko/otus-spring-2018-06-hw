package ru.otus.spring.kushchenko.hw18.controller

import org.springframework.web.bind.annotation.*
import ru.otus.spring.kushchenko.hw18.model.User
import ru.otus.spring.kushchenko.hw18.service.UserService

/**
 * Created by Елена on Июль, 2018
 */
@RestController
@RequestMapping("/users")
class UserController(private val service: UserService) {

    @GetMapping
    fun getAll(): List<User> =
        service.getAll()

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: String): User =
        service.get(id)

    @PostMapping
    fun create(@RequestBody user: User): User =
        service.create(user)

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: String,
               @RequestBody user: User): User =
        service.update(user.copy(id = id))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: String) =
        service.delete(id)

}