package ru.otus.spring.kushchenko.hw6.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.spring.kushchenko.hw6.dto.BookRequest
import ru.otus.spring.kushchenko.hw6.entity.Book
import ru.otus.spring.kushchenko.hw6.service.BookService

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
    fun get(@PathVariable("id") id: Int): Book =
        service.get(id)

    @PostMapping
    fun create(@RequestBody book: BookRequest): Book =
        service.create(Book(book))

    @PutMapping("/{id}")
    fun update(
        @PathVariable("id") id: Int,
        @RequestBody book: BookRequest
    ): Book =
        service.update(id, Book(book))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Int) =
        service.delete(id)
}