package ru.otus.spring.kushchenko.hw8.controller

import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*
import ru.otus.spring.kushchenko.hw8.model.Book
import ru.otus.spring.kushchenko.hw8.service.BookService

/**
 * Created by Елена on Июль, 2018
 */
@RestController
@RequestMapping("/books")
class BookController(private val service: BookService) {

    @GetMapping
    fun getAll(): List<Book> =
        service.getAll()

    @GetMapping("/paged")
    fun getPaged(
        @PathVariable(required = false) page: Int?,
        @PathVariable(required = false) count: Int?
    ): Page<Book> =
        service.getPaged(page ?: 1, count ?: 20)

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: String): Book =
        service.get(id)

    @PostMapping
    fun create(@RequestBody book: Book): Book =
        service.create(book)

    @PutMapping("/{id}")
    fun update(
        @PathVariable("id") id: String,
        @RequestBody book: Book
    ): Book =
        service.update(book.copy(id = id))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: String) =
        service.delete(id)
}