package ru.otus.spring.kushchenko.adminclient.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.spring.kushchenko.adminclient.service.GenreService

/**
 * Created by Елена on Июль, 2018
 */
@RestController
@RequestMapping("/genres")
class GenreController(private val service: GenreService) {

    @GetMapping
    fun getGenres(): List<String> =
        service.getAll()
}