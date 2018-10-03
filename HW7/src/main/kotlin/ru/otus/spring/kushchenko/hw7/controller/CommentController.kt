package ru.otus.spring.kushchenko.hw7.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.spring.kushchenko.hw7.model.dto.CommentDto
import ru.otus.spring.kushchenko.hw7.service.CommentService

/**
 * Created by Елена on Июль, 2018
 */
@RestController
@RequestMapping("/comments")
class CommentController(private val service: CommentService) {

    @GetMapping
    fun getAll(): List<CommentDto> =
        service.getAll()

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: Int): CommentDto =
        service.get(id)

    @PostMapping
    fun create(@RequestBody comment: CommentDto): CommentDto =
        service.create(comment)

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: Int,
               @RequestBody comment: CommentDto): CommentDto =
        service.update(comment.copy(id = id))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Int) =
        service.delete(id)
}