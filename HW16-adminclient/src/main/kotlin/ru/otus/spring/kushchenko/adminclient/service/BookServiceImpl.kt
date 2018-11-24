package ru.otus.spring.kushchenko.adminclient.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.otus.spring.kushchenko.adminclient.model.Book
import ru.otus.spring.kushchenko.adminclient.repository.BookRepository
import ru.otus.spring.kushchenko.adminclient.repository.UserRepository

/**
 * Created by Елена on Июль, 2018
 */
@Service
class BookServiceImpl(private val bookRepository: BookRepository,
                      private val userRepository: UserRepository) : BookService {
    override fun getAll(): List<Book> = bookRepository.findAll()

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
        book.user?.let {
            val user = userRepository.findById(it.id!!)
                .orElseThrow { IllegalArgumentException("User with id = ${it.id} not found") }
            book.user = user
        }

        return bookRepository.save(book)
    }

    override fun update(book: Book): Book {
        val id = book.id!!

        if (bookRepository.existsById(id).not())
            throw IllegalArgumentException("Book with id = $id not found")

        book.user?.let {
            val user = userRepository.findById(it.id!!)
                .orElseThrow { IllegalArgumentException("User with id = ${it.id} not found") }
            book.user = user
        }

        return bookRepository.save(book)
    }

    override fun delete(id: String) = bookRepository.deleteById(id)
}