package ru.otus.spring.kushchenko.hw11.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import ru.otus.spring.kushchenko.hw11.model.Author
import ru.otus.spring.kushchenko.hw11.service.AuthorService

/**
 * Created by Елена on Июль, 2018
 */
@RestController
@RequestMapping("/authors")
class AuthorController(private val service: AuthorService) {

    @GetMapping
    fun getAuthors(): Flux<Author> =
        service.getAll()
}