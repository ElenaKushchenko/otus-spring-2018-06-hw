package ru.otus.spring.kushchenko.hw7.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.spring.kushchenko.hw7.model.Genre
import ru.otus.spring.kushchenko.hw7.service.GenreService

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
    fun create(@RequestBody genre: Genre): Genre =
        service.create(genre)

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: Int,
               @RequestBody genre: Genre): Genre =
        service.update(genre.copy(id = id))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Int) =
        service.delete(id)
}