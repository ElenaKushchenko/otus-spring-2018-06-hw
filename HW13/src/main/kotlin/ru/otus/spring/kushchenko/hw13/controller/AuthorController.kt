package ru.otus.spring.kushchenko.hw13.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import ru.otus.spring.kushchenko.hw13.service.AuthorService

/**
 * Created by Елена on Июль, 2018
 */
@Controller
@RequestMapping("/authors")
class AuthorController(private val service: AuthorService) {

    @GetMapping
    fun getAuthors(model: Model): String {
        model.addAttribute("authors", service.getAll())
        return "authors"
    }
}