package ru.otus.spring.kushchenko.hw6.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.spring.kushchenko.hw6.dto.AuthorRequest
import ru.otus.spring.kushchenko.hw6.entity.Author
import ru.otus.spring.kushchenko.hw6.service.AuthorService

/**
 * Created by Елена on Июль, 2018
 */
@RestController
@RequestMapping("/authors")
class AuthorController(private val service: AuthorService) {

    @GetMapping
    fun getAll(): List<Author> =
        service.getAll()

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: Int): Author =
        service.get(id)

    @PostMapping
    fun create(@RequestBody author: AuthorRequest): Author =
        service.create(Author(author))

    @PutMapping("/{id}")
    fun update(
        @PathVariable("id") id: Int,
        @RequestBody author: AuthorRequest
    ): Author =
        service.update(id, Author(author))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Int) =
        service.delete(id)
}