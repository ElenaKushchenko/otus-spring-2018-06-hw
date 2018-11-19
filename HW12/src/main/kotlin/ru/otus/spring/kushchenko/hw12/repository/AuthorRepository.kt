package ru.otus.spring.kushchenko.hw12.repository

interface AuthorRepository {
    fun getAll(): List<String>
}