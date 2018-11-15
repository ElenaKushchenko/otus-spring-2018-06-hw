package ru.otus.spring.kushchenko.hw9.repository

interface AuthorRepository {
    fun getAll(): List<String>
}