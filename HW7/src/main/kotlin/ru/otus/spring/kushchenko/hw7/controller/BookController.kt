package ru.otus.spring.kushchenko.hw7.controller

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
import ru.otus.spring.kushchenko.hw7.model.Book
import ru.otus.spring.kushchenko.hw7.model.ShortBook
import ru.otus.spring.kushchenko.hw7.service.BookService

/**
 * Created by Елена on Июль, 2018
 */
@RestController
@RequestMapping("/books")
class BookController(private val service: BookService) {

    @GetMapping("/all")
    fun getAll(): List<ShortBook> =
        service.getShortBooks()

    @GetMapping
    fun find(
        @RequestParam(value = "name", required = false) name: String?,
        @RequestParam(value = "authorId", required = false) authorId: Int?,
        @RequestParam(value = "genreId", required = false) genreId: Int?,
        @RequestParam(value = "page", required = false, defaultValue = "1") page: Int,
        @RequestParam(value = "size", required = false, defaultValue = "20") size: Int
    ): Page<ShortBook> =
        service.find(name, authorId, genreId, page, size)

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: Int): Book =
        service.get(id)

    @PostMapping
    fun create(@RequestBody book: Book): Book =
        service.create(book)

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: Int,
               @RequestBody book: Book): Book =
        service.update(book.copy(id = id))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Int) =
        service.delete(id)
}