package ru.otus.spring.kushchenko.hw5.repository

import ru.otus.spring.kushchenko.hw5.model.Genre

/**
 * Created by Елена on Июль, 2018
 */
interface GenreRepository {
    fun getAll(): List<Genre>
    fun get(id: Int): Genre?
    fun create(genre: Genre): Genre
    fun update(id: Int, genre: Genre): Genre
    fun delete(id: Int)
    fun findByBookId(bookId: Int): List<Genre>
}