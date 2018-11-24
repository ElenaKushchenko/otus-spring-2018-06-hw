package ru.otus.spring.kushchenko.adminclient.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.otus.spring.kushchenko.adminclient.repository.BookRepository
import ru.otus.spring.kushchenko.adminclient.repository.UserRepository
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

        if (book.user != null) throw IllegalStateException("Book with id = $bookId already taken")

        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User with id = $bookId not found") }

        book.user = user
        bookRepository.save(book)
    }

    override fun returnBook(bookId: String, userId: String) {
        log.debug("Returning book with id = $bookId by user with id = $userId")

        val book = bookRepository.findById(bookId)
            .orElseThrow { IllegalArgumentException("Book with id = $bookId not found") }

        book.user ?: throw IllegalStateException("Book with id = $bookId already free")

        val user = userRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User with id = $bookId not found") }

        book.user = null
        bookRepository.save(book)
    }
}