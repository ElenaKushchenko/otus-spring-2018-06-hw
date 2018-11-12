package ru.otus.spring.kushchenko.adminclient.controller

import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.*
import ru.otus.spring.kushchenko.adminclient.model.Comment
import ru.otus.spring.kushchenko.adminclient.service.CommentService

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
        @RequestParam(value = "userId", required = false) userId: String?,
        @RequestParam(value = "bookId", required = false) bookId: String?,
        @RequestParam(value = "page", required = false, defaultValue = "1") page: Int,
        @RequestParam(value = "size", required = false, defaultValue = "20") size: Int,
        @RequestParam(value = "sortBy", required = false, defaultValue = "name") sortBy: String,
        @RequestParam(value = "dir", required = false, defaultValue = "ASC") dir: String
    ): Page<Comment> =
        service.getFiltered(userId, bookId, page, size, sortBy, dir)

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