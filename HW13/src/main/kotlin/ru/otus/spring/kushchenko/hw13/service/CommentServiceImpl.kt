package ru.otus.spring.kushchenko.hw13.service

import org.springframework.stereotype.Service
import ru.otus.spring.kushchenko.hw13.model.Comment
import ru.otus.spring.kushchenko.hw13.repository.BookRepository

@Service
class CommentServiceImpl(private val bookRepository: BookRepository) : CommentService {

    override fun create(bookId: String, comment: Comment): Comment {
        val book = bookRepository.findById(bookId)
            .orElseThrow { IllegalArgumentException("Book with id = $bookId not found") }
        val updatedBook = book.copy(comments = (book.comments ?: emptyList()).plus(comment))

        bookRepository.save(updatedBook)
        return comment
    }
}