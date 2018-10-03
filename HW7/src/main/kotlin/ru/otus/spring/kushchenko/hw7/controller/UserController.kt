package ru.otus.spring.kushchenko.hw7.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.spring.kushchenko.hw7.model.User
import ru.otus.spring.kushchenko.hw7.service.UserService

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
    fun create(@RequestBody user: User): User =
        service.create(user)

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: Int,
               @RequestBody user: User): User =
        service.update(user.copy(id = id))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Int) =
        service.delete(id)

}