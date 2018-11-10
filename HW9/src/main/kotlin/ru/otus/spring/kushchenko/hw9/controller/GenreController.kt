package ru.otus.spring.kushchenko.hw9.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import ru.otus.spring.kushchenko.hw9.model.Genre
import ru.otus.spring.kushchenko.hw9.service.GenreService

/**
 * Created by Елена on Июль, 2018
 */
@Controller
@RequestMapping("/genres")
class GenreController(private val service: GenreService) {

    @GetMapping
    fun getAll(model: Model): String {
        model.addAttribute("genres", service.getAll())
        return "genres"
    }

    @PostMapping
    fun create(@RequestBody genre: Genre, model: Model): Genre =
        service.create(genre)

    @GetMapping("/{id}/delete")
    fun delete(@PathVariable("id") id: Int, model: Model): String {
        service.delete(id)
        return getAll(model)
    }
}