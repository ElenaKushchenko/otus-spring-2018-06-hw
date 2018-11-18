package ru.otus.spring.kushchenko.hw12.service

import org.springframework.data.domain.Page
import org.springframework.security.access.prepost.PreAuthorize
import ru.otus.spring.kushchenko.hw12.model.Book
import ru.otus.spring.kushchenko.hw12.model.ShortBook

/**
 * Created by Елена on Июль, 2018
 */
@PreAuthorize("isAuthenticated()")
interface BookService {
    fun getAll(): List<ShortBook>
    fun getPaged(page: Int, size: Int, sortBy: String, dir: String): Page<Book>
    fun get(id: String): Book
    @PreAuthorize("hasRole('ADMIN')")
    fun create(book: Book): Book
    @PreAuthorize("hasRole('ADMIN')")
    fun update(book: Book): Book
    fun delete(id: String)
}