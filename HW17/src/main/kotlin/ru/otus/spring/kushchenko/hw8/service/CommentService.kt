package ru.otus.spring.kushchenko.hw8.service

import org.springframework.data.domain.Page
import ru.otus.spring.kushchenko.hw8.model.Comment

/**
 * Created by Елена on Июль, 2018
 */
interface CommentService {
    fun getAll(): List<Comment>
    fun getFiltered(userId: String?, bookId: String?, page: Int, size: Int, sortBy: String, dir: String): Page<Comment>
    fun get(id: String): Comment
    fun create(comment: Comment): Comment
    fun update(comment: Comment): Comment
    fun delete(id: String)
}