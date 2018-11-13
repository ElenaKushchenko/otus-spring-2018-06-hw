package ru.otus.spring.kushchenko.hw11.service

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.otus.spring.kushchenko.hw11.model.Book
import ru.otus.spring.kushchenko.hw11.repository.BookRepository

/**
 * Created by Елена on Июль, 2018
 */
@Service
class BookServiceImpl(private val bookRepository: BookRepository) : BookService {
    override fun getAll(): Flux<Book> = bookRepository.findAll()

    override fun getPaged(page: Int, size: Int, sortBy: String, dir: String): Flux<Book> {
        val pageable = PageRequest.of(
            page - 1,
            size,
            Sort(Sort.Direction.valueOf(dir), sortBy)
        )

        return bookRepository.getPaged(pageable)
    }

    override fun get(id: String): Mono<Book> =
        bookRepository.findById(id)
            .switchIfEmpty(Mono.error(IllegalArgumentException("Book with id = $id not found")))

    override fun create(book: Book): Mono<Book> {
        book.id?.let {
            bookRepository.existsById(it)
                .doOnNext {  }
                .switchIfEmpty(Mono.error(IllegalArgumentException("Book with id = $it not found")))
        }

        return bookRepository.save(book)
    }

    override fun update(book: Book): Mono<Book> {
        val id = book.id!!

        bookRepository.existsById(book.id)
            .doOnNext { exists ->
                if (exists.not())
                    throw IllegalArgumentException("Book with id = $id not found")
            }

        return bookRepository.save(book)
    }

    override fun delete(id: String) = bookRepository.deleteById(id)
}