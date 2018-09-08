package ru.otus.spring.kushchenko.hw7.service

import ru.otus.spring.kushchenko.hw7.entity.Book

/**
 * Created by Елена on Июль, 2018
 */
interface BookService {
    fun getAll(): List<Book>
    fun get(id: Int): Book
    fun create(book: Book): Book
    fun update(book: Book): Book
    fun delete(id: Int)
}