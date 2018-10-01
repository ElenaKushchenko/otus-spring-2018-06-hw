package ru.otus.spring.kushchenko.hw8.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.otus.spring.kushchenko.hw8.repository.BookRepository
import ru.otus.spring.kushchenko.hw8.repository.UserRepository
import java.lang.IllegalArgumentException

/**
 * Created by Елена on Июль, 2018
 */
@Service
class LibraryServiceImpl(
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository
) : LibraryService {
    private val log = LoggerFactory.getLogger(LibraryServiceImpl::class.java)

    override fun takeBook(bookId: String, userId: String) {
        log.debug("Taking book with id = $bookId by user with id = $userId")

        val book = bookRepository.findById(bookId)
            .orElseThrow { IllegalArgumentException("Book with id = $bookId not found") }

        if (book.userId != null) throw IllegalStateException("Book with id = $bookId already taken")

        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User with id = $bookId not found") }

        book.userId = user.id
        bookRepository.save(book)
    }

    override fun returnBook(bookId: String, userId: String) {
        log.debug("Returning book with id = $bookId by user with id = $userId")

        val book = bookRepository.findById(bookId)
            .orElseThrow { IllegalArgumentException("Book with id = $bookId not found") }

        book.userId ?: throw IllegalStateException("Book with id = $bookId already free")

        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User with id = $bookId not found") }

        book.userId = null
        bookRepository.save(book)
    }
}