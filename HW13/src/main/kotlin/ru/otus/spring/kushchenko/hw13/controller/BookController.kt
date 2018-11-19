package ru.otus.spring.kushchenko.hw13.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import ru.otus.spring.kushchenko.hw13.model.Book
import ru.otus.spring.kushchenko.hw13.service.BookService

/**
 * Created by Елена on Июль, 2018
 */
@Controller
@RequestMapping("/books")
class BookController(private val service: BookService) {

    @GetMapping
    fun getAll(model: Model): String {
        model.addAttribute("books", service.getAll())
        return "books"
    }

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: String, model: Model): String {
        model.addAttribute("book", service.get(id))
        return "book"
    }

    @PostMapping
    fun create(
        @RequestParam("name") name: String,
        @RequestParam("originalName") originalName: String,
        @RequestParam("paperback") paperback: Int,
        @RequestParam("authors") authors: String,
        @RequestParam("genres") genres: String,
        model: Model
    ): String {
        val book = Book(
            name = name,
            originalName = if (originalName.isNotBlank()) originalName else null,
            paperback = paperback,
            authors = if (authors.isNotBlank()) authors.split(",") else null,
            genres = if (genres.isNotBlank()) genres.split(",") else null
        )

        service.create(book)
        return "redirect:/books"
    }

    @GetMapping("/{id}/delete")
    fun delete(@PathVariable("id") id: String, model: Model): String {
        service.delete(id)
        return "redirect:/books"
    }
}