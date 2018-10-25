package ru.otus.spring.kushchenko.hw7.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.otus.spring.kushchenko.hw7.model.Book
import ru.otus.spring.kushchenko.hw7.model.ShortBook
import ru.otus.spring.kushchenko.hw7.repository.BookRepository

/**
 * Created by Елена on Июль, 2018
 */
@Service
//@Transactional
class BookServiceImpl(private val bookRepository: BookRepository) : BookService {
    override fun getAllShortBooks(): List<ShortBook> = bookRepository.findAllShortBooks()

    override fun find(name: String?, authorId: Int?, genreId: Int?, page: Int, size: Int): Page<ShortBook> {
        val pageable = PageRequest.of(page - 1, size)

        return bookRepository.find(
            name,
            authorId,
            genreId,
            pageable
        )
    }

    override fun get(id: Int): Book = bookRepository.findById(id)
        .orElseThrow { IllegalArgumentException("Book with id = $id not found") }

    override fun create(book: Book): Book = bookRepository.save(book)

    override fun update(book: Book): Book {
        val id = book.getId()!!

        if (bookRepository.existsById(id).not())
            throw IllegalArgumentException("Book with id = $id not found")

        return bookRepository.save(book)
    }

    override fun delete(id: Int) = bookRepository.deleteById(id)
}