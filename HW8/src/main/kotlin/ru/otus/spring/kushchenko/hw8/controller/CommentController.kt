package ru.otus.spring.kushchenko.hw8.controller

import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*
import ru.otus.spring.kushchenko.hw8.model.Comment
import ru.otus.spring.kushchenko.hw8.service.CommentService

/**
 * Created by Елена on Июль, 2018
 */
@RestController
@RequestMapping("/comments")
class CommentController(private val service: CommentService) {

    @GetMapping
    fun getAll(): List<Comment> =
        service.getAll()

    @GetMapping("/paged")
    fun getFiltered(
        @PathVariable(required = false) userId: String?,
        @PathVariable(required = false) bookId: String?,
        @PathVariable(required = false) page: Int?,
        @PathVariable(required = false) count: Int?
    ): Page<Comment> =
        service.getFiltered(userId, bookId, page ?: 1, count ?: 20)

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: String): Comment =
        service.get(id)

    @PostMapping
    fun create(@RequestBody comment: Comment): Comment =
        service.create(comment)

    @PutMapping("/{id}")
    fun update(
        @PathVariable("id") id: String,
        @RequestBody comment: Comment
    ): Comment =
        service.update(comment.copy(id = id))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: String) =
        service.delete(id)
}