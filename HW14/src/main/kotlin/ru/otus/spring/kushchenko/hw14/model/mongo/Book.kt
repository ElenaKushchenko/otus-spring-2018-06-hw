package ru.otus.spring.kushchenko.hw14.model.mongo

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Created by Елена on Июль, 2018
 */
@Document(collection = "books")
data class Book(
    @Id
    val id: String? = null,
    val name: String,
    val originalName: String? = null,
    val paperback: Int? = null,
    val authors: List<String>? = emptyList(),
    val genres: List<String>? = emptyList(),
    val comments: List<Comment>? = emptyList()
)