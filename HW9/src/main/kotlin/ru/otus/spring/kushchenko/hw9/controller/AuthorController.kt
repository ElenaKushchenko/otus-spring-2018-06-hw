package ru.otus.spring.kushchenko.hw9.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import ru.otus.spring.kushchenko.hw9.model.Author
import ru.otus.spring.kushchenko.hw9.service.AuthorService

/**
 * Created by Елена on Июль, 2018
 */
@Controller
@RequestMapping("/authors")
class AuthorController(private val service: AuthorService) {

    @GetMapping
    fun getAll(model: Model): String {
        model.addAttribute("authors", service.getAll())
        return "authors"
    }

    @PostMapping
    fun create(@RequestBody author: Author, model: Model): Author =
        service.create(author)

    @GetMapping("/{id}/delete")
    fun delete(@PathVariable("id") id: Int, model: Model): String {
        service.delete(id)
        return getAll(model)
    }
}