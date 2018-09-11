package ru.otus.spring.kushchenko.hw6.repository

import ru.otus.spring.kushchenko.hw6.entity.Book

/**
 * Created by Елена on Июль, 2018
 */
interface BookRepository {
    fun getAll(): List<Book>
    fun get(id: Int): Book
    fun create(book: Book): Book
    fun update(id: Int, book: Book): Book
    fun delete(id: Int)
}