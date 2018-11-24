package ru.otus.spring.kushchenko.adminclient.controller

import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.otus.spring.kushchenko.adminclient.model.Book
import ru.otus.spring.kushchenko.adminclient.service.BookService

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
        @RequestParam(value = "page", required = false, defaultValue = "1") page: Int,
        @RequestParam(value = "size", required = false, defaultValue = "20") size: Int,
        @RequestParam(value = "sortBy", required = false, defaultValue = "name") sortBy: String,
        @RequestParam(value = "dir", required = false, defaultValue = "ASC") dir: String
    ): Page<Book> =
        service.getPaged(page, size, sortBy, dir)

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