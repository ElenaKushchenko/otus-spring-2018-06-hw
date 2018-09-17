package ru.otus.spring.kushchenko.hw8.dto

/**
 * Created by Елена on Авг., 2018
 */
data class BookRequest(
    var name: String,
    var originalName: String? = null,
    var paperback: Int? = null,
    var authorIds: List<Int>? = emptyList(),
    var genreIds: List<Int>? = emptyList()
)