package ru.otus.spring.kushchenko.hw18.repository

interface GenreRepository {
    fun getAll(): List<String>
}