package ru.otus.spring.kushchenko.hw6.repository

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 * Created by Елена on Июль, 2018
 */
@Component
class LibraryDaoImpl(
    private val bookRepository: BookRepository,
    private val userRepository: UserRepository
) : LibraryDao {
    private val log = LoggerFactory.getLogger(LibraryDaoImpl::class.java)

    override fun takeBook(bookId: Int, userId: Int) {
        log.debug("Taking book with id = $bookId by user with id = $userId")

        val book = bookRepository.get(bookId)
        if (book.user != null) throw IllegalStateException("Book with id = $bookId already taken")
        val user = userRepository.get(userId)

        book.user = user
        bookRepository.update(bookId, book)
    }

    override fun returnBook(bookId: Int, userId: Int) {
        log.debug("Returning book with id = $bookId by user with id = $userId")

        val book = bookRepository.get(bookId)
        book.user ?: throw IllegalStateException("Book with id = $bookId already free")
        val user = userRepository.get(userId)

        book.user = null
        bookRepository.update(bookId, book)
    }
}