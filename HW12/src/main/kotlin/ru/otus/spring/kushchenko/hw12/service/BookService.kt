package ru.otus.spring.kushchenko.hw12.service

import org.springframework.data.domain.Page
import ru.otus.spring.kushchenko.hw12.model.Book
import ru.otus.spring.kushchenko.hw12.model.ShortBook

/**
 * Created by Елена on Июль, 2018
 */
interface BookService {
    fun getAll(): List<ShortBook>
    fun getPaged(page: Int, size: Int, sortBy: String, dir: String): Page<Book>
    fun get(id: String): Book
    fun create(book: Book): Book
    fun update(book: Book): Book
    fun delete(id: String)
}