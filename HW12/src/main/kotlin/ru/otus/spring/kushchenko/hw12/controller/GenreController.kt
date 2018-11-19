package ru.otus.spring.kushchenko.hw12.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import ru.otus.spring.kushchenko.hw12.service.GenreService

/**
 * Created by Елена on Июль, 2018
 */
@Controller
@RequestMapping("/genres")
class GenreController(private val service: GenreService) {

    @GetMapping
    fun getGenres(model: Model): String {
        model.addAttribute("genres", service.getAll())
        return "genres"
    }
}