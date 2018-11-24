package ru.otus.spring.kushchenko.adminclient.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.otus.spring.kushchenko.adminclient.service.LibraryService

/**
 * Created by Елена on Июль, 2018
 */
@RestController
@RequestMapping("/library")
class LibraryController(private val service: LibraryService) {

    @PostMapping("/take")
    fun takeBook(
        @RequestParam("bookId") bookId: String,
        @RequestParam("userId") userId: String
    ) =
        service.takeBook(bookId, userId)

    @DeleteMapping("/return")
    fun returnBook(
        @RequestParam("bookId") bookId: String,
        @RequestParam("userId") userId: String
    ) =
        service.returnBook(bookId, userId)
}