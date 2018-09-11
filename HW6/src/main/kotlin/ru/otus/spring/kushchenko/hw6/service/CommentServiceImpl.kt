package ru.otus.spring.kushchenko.hw6.service

import org.springframework.stereotype.Service
import ru.otus.spring.kushchenko.hw6.entity.Comment
import ru.otus.spring.kushchenko.hw6.repository.CommentRepository

/**
 * Created by Елена on Июль, 2018
 */
@Service
class CommentServiceImpl(private val commentRepository: CommentRepository) : CommentService {
    override fun getAll() = commentRepository.getAll()

    override fun get(id: Int) = commentRepository.get(id)

    override fun create(comment: Comment) = commentRepository.create(comment)

    override fun update(id: Int, comment: Comment) = commentRepository.update(id, comment)

    override fun delete(id: Int) = commentRepository.delete(id)
}