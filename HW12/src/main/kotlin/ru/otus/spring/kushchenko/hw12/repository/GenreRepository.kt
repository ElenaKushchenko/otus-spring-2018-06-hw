package ru.otus.spring.kushchenko.hw12.repository

interface GenreRepository {
    fun getAll(): List<String>
}