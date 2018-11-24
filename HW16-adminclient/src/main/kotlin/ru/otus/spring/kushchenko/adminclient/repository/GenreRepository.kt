package ru.otus.spring.kushchenko.adminclient.repository

interface GenreRepository {
    fun getAll(): List<String>
}