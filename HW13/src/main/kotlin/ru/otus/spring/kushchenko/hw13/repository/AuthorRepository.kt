package ru.otus.spring.kushchenko.hw13.repository

interface AuthorRepository {
    fun getAll(): List<String>
}