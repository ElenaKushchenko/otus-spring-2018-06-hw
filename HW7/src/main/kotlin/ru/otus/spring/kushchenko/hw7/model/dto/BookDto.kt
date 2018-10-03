package ru.otus.spring.kushchenko.hw7.model.dto

/**
 * Created by Елена on Авг., 2018
 */
data class BookDto(
    val id: Int? = null,
    var name: String,
    var originalName: String? = null,
    var paperback: Int? = null,
    var authorIds: List<Int>? = emptyList(),
    var genreIds: List<Int>? = emptyList(),
    var userId: Int? = null,
    var commentIds: List<Int>? = emptyList()
)