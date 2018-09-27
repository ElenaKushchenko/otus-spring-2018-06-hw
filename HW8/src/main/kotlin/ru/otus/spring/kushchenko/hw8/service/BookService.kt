package ru.otus.spring.kushchenko.hw8.service

import ru.otus.spring.kushchenko.hw8.entity.Book

/**
 * Created by Елена on Июль, 2018
 */
interface BookService {
    fun getAll(): List<Book>
    fun get(id: String): Book
    fun create(book: Book): Book
    fun update(book: Book): Book
    fun delete(id: String)
}