package ru.otus.spring.kushchenko.hw6.repository

import ru.otus.spring.kushchenko.hw6.entity.Genre

/**
 * Created by Елена on Июль, 2018
 */
interface GenreRepository {
    fun getAll(): List<Genre>
    fun get(id: Int): Genre
    fun create(genre: Genre): Genre
    fun update(id: Int, genre: Genre): Genre
    fun delete(id: Int)
}