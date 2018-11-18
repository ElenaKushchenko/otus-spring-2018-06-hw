package ru.otus.spring.kushchenko.hw13.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.otus.spring.kushchenko.hw13.model.Book
import ru.otus.spring.kushchenko.hw13.model.ShortBook
import ru.otus.spring.kushchenko.hw13.repository.BookRepository

/**
 * Created by Елена on Июль, 2018
 */
@Service
class BookServiceImpl(private val bookRepository: BookRepository) : BookService {
    override fun getAll(): List<ShortBook> = bookRepository.findAllShortBooks()

    override fun getPaged(page: Int, size: Int, sortBy: String, dir: String): Page<Book> {
        val pageable = PageRequest.of(
            page - 1,
            size,
            Sort(Sort.Direction.valueOf(dir), sortBy)
        )

        return bookRepository.findAll(pageable)
    }

    override fun get(id: String): Book = bookRepository.findById(id)
        .orElseThrow { IllegalArgumentException("Book with id = $id not found") }

    override fun create(book: Book): Book {
        book.id?.let {
            if (bookRepository.existsById(it))
                throw IllegalArgumentException("Book with id = $it already exists")
        }

        return bookRepository.save(book)
    }

    override fun update(book: Book): Book {
        val id = book.id!!

        if (bookRepository.existsById(id).not())
            throw IllegalArgumentException("Book with id = $id not found")

        return bookRepository.save(book)
    }

    override fun delete(id: String) = bookRepository.deleteById(id)
}