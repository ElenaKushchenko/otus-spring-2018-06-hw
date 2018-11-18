package ru.otus.spring.kushchenko.hw13.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import ru.otus.spring.kushchenko.hw13.model.Comment
import ru.otus.spring.kushchenko.hw13.service.CommentService
import java.security.Principal

/**
 * Created by Елена on Июль, 2018
 */
@Controller
@RequestMapping("/books/{bookId}/comments")
class CommentController(private val bookController: BookController,
                        private val service: CommentService) {

    @PostMapping
    fun create(
        @PathVariable("bookId") bookId: String,
        text: String,
        username: Principal,
        model: Model
    ): String {
        val comment = Comment(text = text, username = username.name)
        service.create(bookId, comment)
        return bookController.get(bookId, model)
    }
}