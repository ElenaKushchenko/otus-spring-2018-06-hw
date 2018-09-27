package ru.otus.spring.kushchenko.hw8.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.otus.spring.kushchenko.hw8.entity.Book
import ru.otus.spring.kushchenko.hw8.repository.BookRepository
import java.lang.IllegalArgumentException

/**
 * Created by Елена on Июль, 2018
 */
@Service
@Transactional
class BookServiceImpl(private val bookRepository: BookRepository) : BookService {
    override fun getAll(): List<Book> = bookRepository.findAll()

    override fun get(id: String): Book = bookRepository.findById(id)
        .orElseThrow { IllegalArgumentException("Book with id = $id not found") }

    override fun create(book: Book): Book = bookRepository.save(book)

    override fun update(book: Book): Book {
        val id = book.id!!

        if (bookRepository.existsById(id).not())
            throw IllegalArgumentException("Book with id = $id not found")

        return bookRepository.save(book)
    }

    override fun delete(id: String) = bookRepository.deleteById(id)
}