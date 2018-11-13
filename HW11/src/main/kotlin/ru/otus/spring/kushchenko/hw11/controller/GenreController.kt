package ru.otus.spring.kushchenko.hw11.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import ru.otus.spring.kushchenko.hw11.service.GenreService

/**
 * Created by Елена on Июль, 2018
 */
@RestController
@RequestMapping("/genres")
class GenreController(private val service: GenreService) {

    @GetMapping
    fun getGenres(): Flux<String> =
        service.getAll()
}