package ru.otus.spring.kushchenko.hw9.service

import org.springframework.stereotype.Service
import ru.otus.spring.kushchenko.hw9.model.Comment
import ru.otus.spring.kushchenko.hw9.repository.CommentRepository

/**
 * Created by Елена on Июль, 2018
 */
@Service
class CommentServiceImpl(private val commentRepository: CommentRepository) : CommentService {
    override fun getAll(): List<Comment> = commentRepository.findAll()

    override fun get(id: Int): Comment = commentRepository.findById(id)
        .orElseThrow { IllegalArgumentException("Comment with id = $id not found") }

    override fun create(comment: Comment) = commentRepository.save(comment)

    override fun update(comment: Comment): Comment {
        val id = comment.id!!

        if (commentRepository.existsById(id).not())
            throw IllegalArgumentException("Comment with id = $id not found")

        return commentRepository.save(comment)
    }

    override fun delete(id: Int) = commentRepository.deleteById(id)
}