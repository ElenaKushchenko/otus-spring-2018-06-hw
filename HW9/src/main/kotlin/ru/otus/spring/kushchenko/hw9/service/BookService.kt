package ru.otus.spring.kushchenko.hw9.service

import org.springframework.data.domain.Page
import ru.otus.spring.kushchenko.hw9.model.Book
import ru.otus.spring.kushchenko.hw9.model.ShortBook

/**
 * Created by Елена on Июль, 2018
 */
interface BookService {
    fun getAllShortBooks(): List<ShortBook>
    fun find(name: String?, authorId: Int?, genreId: Int?, page: Int, size: Int): Page<ShortBook>
    fun get(id: Int): Book
    fun create(book: Book): Book
    fun update(book: Book): Book
    fun delete(id: Int)
}