package ru.otus.spring.kushchenko.hw8.repository

interface GenreRepository {
    fun getAll(): List<String>
}