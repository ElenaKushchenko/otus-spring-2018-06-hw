package ru.otus.spring.kushchenko.hw13.repository

interface GenreRepository {
    fun getAll(): List<String>
}