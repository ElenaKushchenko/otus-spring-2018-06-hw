package ru.otus.spring.kushchenko.adminclient.repository

import ru.otus.spring.kushchenko.adminclient.model.Author

interface AuthorRepository {
    fun getAll(): List<Author>
}