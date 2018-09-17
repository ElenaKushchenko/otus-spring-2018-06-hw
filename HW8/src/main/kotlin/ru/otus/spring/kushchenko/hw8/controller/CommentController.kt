package ru.otus.spring.kushchenko.hw8.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.spring.kushchenko.hw8.entity.Comment
import ru.otus.spring.kushchenko.hw8.service.CommentService

/**
 * Created by Елена on Июль, 2018
 */
//@RestController
//@RequestMapping("/comments")
//class CommentController(private val service: CommentService) {
//
//    @GetMapping
//    fun getAll(): List<Comment> =
//        service.getAll()
//
////    @PostMapping
////    fun create(@RequestBody comment: CommentRequest): Comment =
////        service.create(Comment(comment))
////
////    @PutMapping("/{id}")
////    fun update(@PathVariable("id") id: Int,
////               @RequestBody comment: CommentRequest): Comment =
////        service.update(Comment(comment, id))
//
//    @DeleteMapping("/{id}")
//    fun delete(@PathVariable("id") id: Int) =
//        service.delete(id)
//}