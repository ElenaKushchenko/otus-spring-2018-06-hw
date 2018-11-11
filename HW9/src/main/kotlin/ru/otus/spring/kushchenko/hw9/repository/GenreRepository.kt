package ru.otus.spring.kushchenko.hw9.repository

interface GenreRepository {
    fun getAll(): List<String>
}