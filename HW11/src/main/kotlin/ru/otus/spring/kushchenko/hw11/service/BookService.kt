package ru.otus.spring.kushchenko.hw11.service

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.otus.spring.kushchenko.hw11.model.Book

/**
 * Created by Елена on Июль, 2018
 */
interface BookService {
    fun getAll(): Flux<Book>
    fun getPaged(page: Int, size: Int, sortBy: String, dir: String): Flux<Book>
    fun get(id: String): Mono<Book>
    fun create(book: Book): Mono<Book>
    fun update(book: Book): Mono<Book>
    fun delete(id: String): Mono<Void>
}