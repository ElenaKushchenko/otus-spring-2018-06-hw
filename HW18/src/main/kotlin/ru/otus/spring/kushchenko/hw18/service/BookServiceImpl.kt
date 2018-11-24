package ru.otus.spring.kushchenko.hw18.service

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.otus.spring.kushchenko.hw18.model.Book
import ru.otus.spring.kushchenko.hw18.repository.BookRepository
import ru.otus.spring.kushchenko.hw18.repository.UserRepository

/**
 * Created by Елена on Июль, 2018
 */
@Service
class BookServiceImpl(private val bookRepository: BookRepository,
                      private val userRepository: UserRepository) : BookService {
    @HystrixCommand(groupKey = "Book", commandKey = "GetAll")
    override fun getAll(): List<Book> = bookRepository.findAll()

    @HystrixCommand(groupKey = "Book", commandKey = "GetPaged")
    override fun getPaged(page: Int, size: Int, sortBy: String, dir: String): Page<Book> {
        val pageable = PageRequest.of(
            page - 1,
            size,
            Sort(Sort.Direction.valueOf(dir), sortBy)
        )

        return bookRepository.findAll(pageable)
    }

    @HystrixCommand(groupKey = "Book", commandKey = "Get")
    override fun get(id: String): Book = bookRepository.findById(id)
        .orElseThrow { IllegalArgumentException("Book with id = $id not found") }

    @HystrixCommand(groupKey = "Book", commandKey = "Create")
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

    @HystrixCommand(groupKey = "Book", commandKey = "Update")
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

    @HystrixCommand(groupKey = "Book", commandKey = "Delete")
    override fun delete(id: String) = bookRepository.deleteById(id)
}