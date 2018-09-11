package ru.otus.spring.kushchenko.hw6.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.spring.kushchenko.hw6.dto.GenreRequest
import ru.otus.spring.kushchenko.hw6.entity.Genre
import ru.otus.spring.kushchenko.hw6.service.GenreService

/**
 * Created by Елена on Июль, 2018
 */
@RestController
@RequestMapping("/genres")
class GenreController(private val service: GenreService) {

    @GetMapping
    fun getAll(): List<Genre> =
        service.getAll()

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: Int): Genre =
        service.get(id)

    @PostMapping
    fun create(@RequestBody genre: GenreRequest): Genre =
        service.create(Genre(genre))

    @PutMapping("/{id}")
    fun update(
        @PathVariable("id") id: Int,
        @RequestBody genre: GenreRequest
    ): Genre =
        service.update(id, Genre(genre))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Int) =
        service.delete(id)
}