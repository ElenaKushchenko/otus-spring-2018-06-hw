package ru.otus.spring.kushchenko.hw7.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.spring.kushchenko.hw7.dto.CommentRequest
import ru.otus.spring.kushchenko.hw7.entity.Comment
import ru.otus.spring.kushchenko.hw7.service.CommentService

/**
 * Created by Елена on Июль, 2018
 */
@RestController
@RequestMapping("/comments")
class CommentController(private val service: CommentService) {

    @GetMapping
    fun getAll(): List<Comment> =
        service.getAll()

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: Int): Comment =
        service.get(id)

    @PostMapping
    fun create(@RequestBody comment: CommentRequest): Comment =
        service.create(Comment(comment))

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: Int,
               @RequestBody comment: CommentRequest): Comment =
        service.update(Comment(comment, id))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Int) =
        service.delete(id)
}