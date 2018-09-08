package ru.otus.spring.kushchenko.hw7.service

import ru.otus.spring.kushchenko.hw7.entity.Author

/**
 * Created by Елена on Июль, 2018
 */
interface AuthorService {
    fun getAll(): List<Author>
    fun get(id: Int): Author
    fun create(author: Author): Author
    fun update(author: Author): Author
    fun delete(id: Int)
}