package ru.otus.spring.kushchenko.hw6.repository

import ru.otus.spring.kushchenko.hw6.entity.Comment

/**
 * Created by Елена on Июль, 2018
 */
interface CommentRepository {
    fun getAll(): List<Comment>
    fun get(id: Int): Comment
    fun create(comment: Comment): Comment
    fun update(id: Int, author: Comment): Comment
    fun delete(id: Int)
}