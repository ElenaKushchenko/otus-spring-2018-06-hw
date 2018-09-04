package ru.otus.spring.kushchenko.hw5.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.spring.kushchenko.hw5.model.Book
import ru.otus.spring.kushchenko.hw5.service.BookService

/**
 * Created by Елена on Июль, 2018
 */
@RestController
@RequestMapping("/books")
class BookController(private val service: BookService) {

    @GetMapping
    fun getAll(): List<Book> =
        service.getAll()

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: Int): Book? =
        service.get(id)

    @PostMapping
    fun create(@RequestBody book: Book): Book =
        service.create(book)

    @PutMapping("/{id}")
    fun update(
        @PathVariable("id") id: Int,
        @RequestBody book: Book
    ): Book =
        service.update(id, book)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Int) =
        service.delete(id)
}