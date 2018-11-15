package ru.otus.spring.kushchenko.hw11.service

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.otus.spring.kushchenko.hw11.model.Comment

/**
 * Created by Елена on Июль, 2018
 */
interface CommentService {
    fun getAll(): Flux<Comment>
    fun getFiltered(userId: String?, bookId: String?, page: Int, size: Int, sortBy: String, dir: String): Flux<Comment>
    fun get(id: String): Mono<Comment>
    fun create(comment: Comment): Mono<Comment>
    fun update(comment: Comment): Mono<Comment>
    fun delete(id: String): Mono<Void>
}