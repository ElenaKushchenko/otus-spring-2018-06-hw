package ru.otus.spring.kushchenko.hw8.service

import ru.otus.spring.kushchenko.hw8.model.Author

/**
 * Created by Елена on Июль, 2018
 */
interface AuthorService {
    fun getAll(): List<Author>
}