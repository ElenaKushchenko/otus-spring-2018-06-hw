package ru.otus.spring.kushchenko.hw8.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Created by Елена on Июль, 2018
 */
@Document(collection = "books")
data class Book(
    @Id
    val id: String? = null,
    var name: String,
    var originalName: String? = null,
    var paperback: Int? = null,
    var authors: List<Author>? = emptyList(),
    var genres: List<String>? = emptyList(),
    @DBRef
    var userId: String? = null
)