package ru.otus.spring.kushchenko.hw18.repository

import ru.otus.spring.kushchenko.hw18.model.Author

interface AuthorRepository {
    fun getAll(): List<Author>
}