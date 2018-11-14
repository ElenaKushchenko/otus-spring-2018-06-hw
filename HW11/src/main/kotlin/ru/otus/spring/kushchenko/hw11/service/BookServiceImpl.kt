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
class BookServiceImpl(private val repository: BookRepository) : BookService {
    override fun getAll(): Flux<Book> = repository.findAll()

    override fun getPaged(page: Int, size: Int, sortBy: String, dir: String): Flux<Book> {
        val pageable = PageRequest.of(
            page - 1,
            size,
            Sort(Sort.Direction.valueOf(dir), sortBy)
        )

        return repository.getPaged(pageable)
    }

    override fun get(id: String): Mono<Book> =
        repository.findById(id)
            .switchIfEmpty(Mono.error(IllegalArgumentException("Book with id = $id not found")))

    override fun create(book: Book): Mono<Book> =
        Mono.just(book.id != null)
            .filter { presented -> presented }
            .flatMap {
                repository.existsById(book.id!!)
                    .filter { exists -> exists }
                    .flatMap { Mono.error<Book>(IllegalArgumentException("Book with id = ${book.id} already exists")) }
            }
            .switchIfEmpty(repository.save(book))

    override fun update(book: Book): Mono<Book> =
        repository.existsById(book.id!!)
            .flatMap { exists ->
                if (exists) repository.save(book)
                else Mono.error(IllegalArgumentException("Book with id = ${book.id} not found"))
            }

    override fun delete(id: String) = repository.deleteById(id)
}