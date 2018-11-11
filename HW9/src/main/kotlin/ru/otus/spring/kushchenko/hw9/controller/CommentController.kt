package ru.otus.spring.kushchenko.hw9.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import ru.otus.spring.kushchenko.hw9.model.Comment
import ru.otus.spring.kushchenko.hw9.service.CommentService

/**
 * Created by Елена on Июль, 2018
 */
@Controller
@RequestMapping("/books/{bookId}/comments")
class CommentController(private val service: CommentService) {

    @PostMapping
    fun create(
        @PathVariable("bookId") bookId: String,
        comment: Comment,
        model: Model
    ): String {
        service.create(bookId, comment)
        return "book"
    }

    @DeleteMapping
    fun delete(
        @PathVariable("bookId") bookId: String,
        comment: Comment,
        model: Model
    ): String {
        service.delete(bookId, comment)
        return "book"
    }
}