package ru.otus.spring.kushchenko.hw11.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.otus.spring.kushchenko.hw11.model.Comment
import ru.otus.spring.kushchenko.hw11.service.CommentService

/**
 * Created by Елена on Июль, 2018
 */
@RestController
@RequestMapping("/comments")
class CommentController(private val service: CommentService) {

    @GetMapping
    fun getAll(): Flux<Comment> =
        service.getAll()

    @GetMapping("/paged")
    fun getFiltered(
        @RequestParam(value = "userId", required = false) userId: String?,
        @RequestParam(value = "bookId", required = false) bookId: String?,
        @RequestParam(value = "page", required = false, defaultValue = "1") page: Int,
        @RequestParam(value = "size", required = false, defaultValue = "20") size: Int,
        @RequestParam(value = "sortBy", required = false, defaultValue = "name") sortBy: String,
        @RequestParam(value = "dir", required = false, defaultValue = "ASC") dir: String
    ): Flux<Comment> =
        service.getFiltered(userId, bookId, page, size, sortBy, dir)

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: String): Mono<Comment> =
        service.get(id)

    @PostMapping
    fun create(@RequestBody comment: Comment): Mono<Comment> =
        service.create(comment)

    @PutMapping("/{id}")
    fun update(
        @PathVariable("id") id: String,
        @RequestBody comment: Comment
    ): Mono<Comment> =
        service.update(comment.copy(id = id))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: String) =
        service.delete(id)
}