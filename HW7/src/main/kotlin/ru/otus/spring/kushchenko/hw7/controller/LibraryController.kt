package ru.otus.spring.kushchenko.hw7.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.otus.spring.kushchenko.hw7.service.LibraryService

/**
 * Created by Елена on Июль, 2018
 */
@RestController
@RequestMapping("/library")
class LibraryController(private val service: LibraryService) {

    @PostMapping("/take")
    fun takeBook(
        @RequestParam("bookId") bookId: Int,
        @RequestParam("userId") userId: Int
    ) =
        service.takeBook(bookId, userId)

    @DeleteMapping("/return")
    fun returnBook(
        @RequestParam("bookId") bookId: Int,
        @RequestParam("userId") userId: Int
    ) =
        service.returnBook(bookId, userId)
}