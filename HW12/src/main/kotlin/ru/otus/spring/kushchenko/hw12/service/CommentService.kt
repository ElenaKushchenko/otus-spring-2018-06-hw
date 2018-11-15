package ru.otus.spring.kushchenko.hw12.service

import ru.otus.spring.kushchenko.hw12.model.Comment

/**
 * Created by Елена on Июль, 2018
 */
interface CommentService {
    fun create(bookId: String, comment: Comment): Comment
}