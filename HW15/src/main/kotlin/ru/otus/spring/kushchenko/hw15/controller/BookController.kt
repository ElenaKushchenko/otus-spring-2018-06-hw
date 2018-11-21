package ru.otus.spring.kushchenko.hw15.controller

import org.springframework.integration.support.MessageBuilder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.spring.kushchenko.hw15.gateway.BookGateway
import ru.otus.spring.kushchenko.hw15.model.Book

/**
 * Created by Елена on Июль, 2018
 */
@RestController
@RequestMapping("/books")
class BookController(private val bookGateway: BookGateway) {

    @GetMapping
    fun getAll(): List<Book> =
        bookGateway.getAll(MessageBuilder.withPayload("getAllStatements").build())
}