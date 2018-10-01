package ru.otus.spring.kushchenko.hw8.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.repository.support.PageableExecutionUtils
import org.springframework.stereotype.Service
import ru.otus.spring.kushchenko.hw8.model.Comment
import ru.otus.spring.kushchenko.hw8.repository.CommentRepository
import java.lang.IllegalArgumentException

@Service
class CommentServiceImpl(
    private val repository: CommentRepository,
    private val mongoTemplate: MongoTemplate
) :
    CommentService {
    override fun getAll(): List<Comment> = repository.findAll()

    override fun getFiltered(userId: String?, bookId: String?, page: Int, count: Int): Page<Comment> {
        val pageable = PageRequest.of(page, count)
        val query = Query().with(pageable)

        userId?.run { query.addCriteria(Criteria.where("user.id").`is`(userId))  }
        bookId?.run { query.addCriteria(Criteria.where("book.id").`is`(bookId))  }

        return PageableExecutionUtils.getPage(
            mongoTemplate.find(query, Comment::class.java),
            pageable
        ) { mongoTemplate.count(query, Comment::class.java) }
    }

    override fun get(id: String): Comment = repository.findById(id)
        .orElseThrow { IllegalArgumentException("Comment with id = $id not found") }

    override fun create(comment: Comment): Comment {
        comment.id?.let {
            if (repository.existsById(it))
                throw IllegalArgumentException("Comment with id = $it already exists")
        }

        return repository.save(comment)
    }

    override fun update(comment: Comment): Comment {
        val id = comment.id!!

        if (repository.existsById(id).not())
            throw IllegalArgumentException("Comment with id = $id not found")

        return repository.save(comment)
    }

    override fun delete(id: String) = repository.deleteById(id)
}