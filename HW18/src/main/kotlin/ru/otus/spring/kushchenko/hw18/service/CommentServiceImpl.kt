package ru.otus.spring.kushchenko.hw18.service

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import ru.otus.spring.kushchenko.hw18.model.Comment
import ru.otus.spring.kushchenko.hw18.repository.BookRepository
import ru.otus.spring.kushchenko.hw18.repository.CommentRepository
import ru.otus.spring.kushchenko.hw18.repository.UserRepository
import java.lang.IllegalArgumentException

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository,
    private val bookRepository: BookRepository,
    private val mongoTemplate: MongoTemplate
) : CommentService {
    @HystrixCommand(groupKey = "Comment", commandKey = "GetAll")
    override fun getAll(): List<Comment> = commentRepository.findAll()

    @HystrixCommand(groupKey = "Comment", commandKey = "getFiltered")
    override fun getFiltered(userId: String?, bookId: String?, page: Int, size: Int, sortBy: String, dir: String): Page<Comment> {
        val query = Query()

        userId?.run { query.addCriteria(Criteria.where("user.id").`is`(userId))  }
        bookId?.run { query.addCriteria(Criteria.where("book.id").`is`(bookId))  }

        val pageable = PageRequest.of(
            page - 1,
            size,
            Sort(Sort.Direction.valueOf(dir), sortBy)
        )
        query.with(pageable)

        val total = mongoTemplate.count(query, Comment::class.java)
        val comments = mongoTemplate.find(query, Comment::class.java)

        return PageImpl<Comment>(comments, pageable, total)
    }

    @HystrixCommand(groupKey = "Comment", commandKey = "Get")
    override fun get(id: String): Comment = commentRepository.findById(id)
        .orElseThrow { IllegalArgumentException("Comment with id = $id not found") }

    @HystrixCommand(groupKey = "Comment", commandKey = "Create")
    override fun create(comment: Comment): Comment {
        comment.id?.let {
            if (commentRepository.existsById(it))
                throw IllegalArgumentException("Comment with id = $it already exists")
        }
        val user = userRepository.findById(comment.user.id!!)
            .orElseThrow { IllegalArgumentException("User with id = ${comment.user.id} not found") }
        val book = bookRepository.findById(comment.book.id!!)
            .orElseThrow { IllegalArgumentException("Book with id = ${comment.book.id} not found") }
        comment.user = user
        comment.book = book

        return commentRepository.save(comment)
    }

    @HystrixCommand(groupKey = "Comment", commandKey = "Update")
    override fun update(comment: Comment): Comment {
        val id = comment.id!!

        if (commentRepository.existsById(id).not())
            throw IllegalArgumentException("Comment with id = $id not found")

        val user = userRepository.findById(comment.user.id!!)
            .orElseThrow { IllegalArgumentException("User with id = ${comment.user.id} not found") }
        val book = bookRepository.findById(comment.book.id!!)
            .orElseThrow { IllegalArgumentException("Book with id = ${comment.book.id} not found") }
        comment.user = user
        comment.book = book

        return commentRepository.save(comment)
    }

    @HystrixCommand(groupKey = "Comment", commandKey = "Delete")
    override fun delete(id: String) = commentRepository.deleteById(id)
}