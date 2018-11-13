package ru.otus.spring.kushchenko.hw11.service

import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.otus.spring.kushchenko.hw11.model.Comment
import ru.otus.spring.kushchenko.hw11.repository.BookRepository
import ru.otus.spring.kushchenko.hw11.repository.CommentRepository
import ru.otus.spring.kushchenko.hw11.repository.UserRepository

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository,
    private val bookRepository: BookRepository,
    private val mongoTemplate: ReactiveMongoTemplate
) :
    CommentService {
    override fun getAll(): Flux<Comment> = commentRepository.findAll()

    override fun getFiltered(
        userId: String?,
        bookId: String?,
        page: Int,
        size: Int,
        sortBy: String,
        dir: String
    ): Flux<Comment> {
        val query = Query()

        userId?.run { query.addCriteria(Criteria.where("user.id").`is`(userId)) }
        bookId?.run { query.addCriteria(Criteria.where("book.id").`is`(bookId)) }

        val pageable = PageRequest.of(
            page - 1,
            size,
            Sort(Sort.Direction.valueOf(dir), sortBy)
        )
        query.with(pageable)

        return mongoTemplate.find(query, Comment::class.java)
    }

    override fun get(id: String): Mono<Comment> {
        return commentRepository.findById(id)
            .doOnNext { exists ->
                exists ?: throw IllegalArgumentException("Comment with id = $id not found")
            }
    }

    override fun create(comment: Comment): Mono<Comment> {
        comment.id?.let {
            commentRepository.existsById(it)
                .doOnNext { exists ->
                    if (exists)
                        throw IllegalArgumentException("Comment with id = $it already exists")
                }
        }
        val user = userRepository.findById(comment.user.id!!)
            .doOnNext { exists ->
                exists ?: throw IllegalArgumentException("User with id = ${comment.user.id} not found")
            }
        val book = bookRepository.findById(comment.book.id!!)
            .doOnNext { exists ->
                exists ?: throw IllegalArgumentException("Book with id = ${comment.book.id} not found")
            }
        comment.user = user.block()!!
        comment.book = book.block()!!

        return commentRepository.save(comment)
    }

    override fun update(comment: Comment): Mono<Comment> {
        val id = comment.id!!

        commentRepository.existsById(id)
            .doOnNext { exists ->
                if (exists.not())
                    throw IllegalArgumentException("Comment with id = $id already exists")
            }

        val user = userRepository.findById(comment.user.id!!)
            .doOnNext { exists ->
                exists ?: throw IllegalArgumentException("User with id = ${comment.user.id} not found")
            }
        val book = bookRepository.findById(comment.book.id!!)
            .doOnNext { exists ->
                exists ?: throw IllegalArgumentException("Book with id = ${comment.book.id} not found")
            }
        comment.user = user.block()!!
        comment.book = book.block()!!

        return commentRepository.save(comment)
    }

    override fun delete(id: String) = commentRepository.deleteById(id)
}