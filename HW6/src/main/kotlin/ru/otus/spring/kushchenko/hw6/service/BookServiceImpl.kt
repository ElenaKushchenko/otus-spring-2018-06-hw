package ru.otus.spring.kushchenko.hw6.service

import org.springframework.stereotype.Service
import ru.otus.spring.kushchenko.hw6.entity.Book
import ru.otus.spring.kushchenko.hw6.repository.BookRepository

/**
 * Created by Елена on Июль, 2018
 */
@Service
class BookServiceImpl(private val bookRepository: BookRepository) : BookService {
    override fun getAll() = bookRepository.getAll()

    override fun get(id: Int) = bookRepository.get(id)

    override fun create(book: Book) = bookRepository.create(book)

    override fun update(id: Int, book: Book) = bookRepository.update(id, book)

    override fun delete(id: Int) = bookRepository.delete(id)
}