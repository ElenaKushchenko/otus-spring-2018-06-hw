package ru.otus.spring.kushchenko.hw9.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
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
@RequestMapping("/comments")
class CommentController(private val service: CommentService) {

    @PostMapping
    fun create(@RequestBody comment: Comment, model: Model): Comment =
        service.create(comment)

    @GetMapping("/{id}/delete")
    fun delete(@PathVariable("id") id: Int, model: Model) =
        service.delete(id)
}