package ru.otus.spring.kushchenko.hw8.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.spring.kushchenko.hw8.model.Author
import ru.otus.spring.kushchenko.hw8.service.AuthorService

/**
 * Created by Елена on Июль, 2018
 */
@RestController
@RequestMapping("/authors")
class AuthorController(private val service: AuthorService) {

    @GetMapping
    fun getAuthors(): List<Author> =
        service.getAll()
}