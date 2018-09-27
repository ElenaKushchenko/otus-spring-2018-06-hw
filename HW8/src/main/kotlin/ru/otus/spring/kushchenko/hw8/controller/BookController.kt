package ru.otus.spring.kushchenko.hw8.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.spring.kushchenko.hw8.entity.Book
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

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: String): Book =
        service.get(id)

//    @PostMapping
//    fun create(@RequestBody book: BookRequest): Book =
//        service.create(Book(book))
//
//    @PutMapping("/{id}")
//    fun update(@PathVariable("id") id: Int,
//               @RequestBody book: BookRequest): Book =
//        service.update(Book(book, id))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: String) =
        service.delete(id)
}