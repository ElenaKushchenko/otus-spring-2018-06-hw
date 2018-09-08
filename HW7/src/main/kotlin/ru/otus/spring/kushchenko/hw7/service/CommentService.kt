package ru.otus.spring.kushchenko.hw7.service

import ru.otus.spring.kushchenko.hw7.entity.Comment

/**
 * Created by Елена on Июль, 2018
 */
interface CommentService {
    fun getAll(): List<Comment>
    fun get(id: Int): Comment
    fun create(comment: Comment): Comment
    fun update(comment: Comment): Comment
    fun delete(id: Int)
}