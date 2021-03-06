package ru.otus.spring.kushchenko.hw6.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.spring.kushchenko.hw6.dto.UserRequest
import ru.otus.spring.kushchenko.hw6.entity.User
import ru.otus.spring.kushchenko.hw6.service.UserService

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
    fun get(@PathVariable("id") id: Int): User =
        service.get(id)

    @PostMapping
    fun create(@RequestBody user: UserRequest): User =
        service.create(User(user))

    @PutMapping("/{id}")
    fun update(
        @PathVariable("id") id: Int,
        @RequestBody user: UserRequest
    ): User =
        service.update(id, User(user))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Int) =
        service.delete(id)

}