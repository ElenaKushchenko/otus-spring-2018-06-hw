package ru.otus.spring.kushchenko.hw6.repository

import ru.otus.spring.kushchenko.hw6.entity.Author

/**
 * Created by Елена on Июль, 2018
 */
interface AuthorRepository {
    fun getAll(): List<Author>
    fun get(id: Int): Author
    fun create(author: Author): Author
    fun update(id: Int, author: Author): Author
    fun delete(id: Int)
}