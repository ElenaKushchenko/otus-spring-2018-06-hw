package ru.otus.spring.kushchenko.hw7.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.spring.kushchenko.hw7.mapper.BookMapper.toDto
import ru.otus.spring.kushchenko.hw7.mapper.BookMapper.toEntity
import ru.otus.spring.kushchenko.hw7.model.dto.BookDto
import ru.otus.spring.kushchenko.hw7.service.BookService

/**
 * Created by Елена on Июль, 2018
 */
@RestController
@RequestMapping("/books")
class BookController(private val service: BookService) {

    @GetMapping
    fun getAll(): List<BookDto> =
        service.getAll().map { it.toDto() }

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: Int): BookDto =
        service.get(id).toDto()

    @PostMapping
    fun create(@RequestBody book: BookDto): BookDto =
        service.create(book.toEntity()).toDto()

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: Int,
               @RequestBody book: BookDto): BookDto =
        service.update(book.copy(id = id).toEntity()).toDto()

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Int) =
        service.delete(id)
}