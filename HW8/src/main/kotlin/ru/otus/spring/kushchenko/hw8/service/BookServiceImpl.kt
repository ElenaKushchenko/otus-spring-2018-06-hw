package ru.otus.spring.kushchenko.hw8.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import ru.otus.spring.kushchenko.hw8.model.Book
import ru.otus.spring.kushchenko.hw8.repository.BookRepository
import java.lang.IllegalArgumentException

/**
 * Created by Елена on Июль, 2018
 */
@Service
class BookServiceImpl(private val repository: BookRepository) : BookService {
    override fun getAll(): List<Book> = repository.findAll()

    override fun getPaged(page: Int, count: Int): Page<Book> {
        val pageable = PageRequest.of(page - 1, count)

        return repository.findAll(pageable)
    }

    override fun get(id: String): Book = repository.findById(id)
        .orElseThrow { IllegalArgumentException("Book with id = $id not found") }

    override fun create(book: Book): Book {
        book.id?.let {
            if (repository.existsById(it))
                throw IllegalArgumentException("Book with id = $it already exists")
        }

        return repository.save(book)
    }

    override fun update(book: Book): Book {
        val id = book.id!!

        if (repository.existsById(id).not())
            throw IllegalArgumentException("Book with id = $id not found")

        return repository.save(book)
    }

    override fun delete(id: String) = repository.deleteById(id)
}