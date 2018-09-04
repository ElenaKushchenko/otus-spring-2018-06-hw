package ru.otus.spring.kushchenko.hw5.model

/**
 * Created by Елена on Июль, 2018
 */
data class Book(
    val id: Int,
    val name: String,
    val originalName: String?,
    val paperback: Int?,
    var authors: List<Author>? = emptyList(),
    var genres: List<Genre>? = emptyList(),
    var reader: Reader? = null
)