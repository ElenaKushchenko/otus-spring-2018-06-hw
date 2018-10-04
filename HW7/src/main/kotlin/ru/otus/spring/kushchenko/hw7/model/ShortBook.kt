package ru.otus.spring.kushchenko.hw7.model

interface ShortBook {
    fun getId(): Int?
    fun getName(): String?
    fun getAuthors(): List<Author>?
    fun getGenres(): List<Genre>?
}