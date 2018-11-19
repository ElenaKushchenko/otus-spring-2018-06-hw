package ru.otus.spring.kushchenko.hw12.model

data class ShortBook(
    val id: String,
    val name: String,
    val authors: List<String>? = emptyList(),
    val genres: List<String>? = emptyList()
)