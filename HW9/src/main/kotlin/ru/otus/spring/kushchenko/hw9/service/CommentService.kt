package ru.otus.spring.kushchenko.hw9.service

import ru.otus.spring.kushchenko.hw9.model.Comment

/**
 * Created by Елена on Июль, 2018
 */
interface CommentService {
    fun create(bookId: String, comment: Comment): Comment
    fun delete(bookId: String, comment: Comment)
}