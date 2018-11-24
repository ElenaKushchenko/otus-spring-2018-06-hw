package ru.otus.spring.kushchenko.hw8.repository

import ru.otus.spring.kushchenko.hw8.model.Author

interface AuthorRepository {
    fun getAll(): List<Author>
}