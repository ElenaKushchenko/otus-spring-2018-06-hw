package ru.otus.spring.kushchenko.hw8.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Created by Елена on Июль, 2018
 */
@Document(collection = "books")
data class Book(
    @Id
    val id: String? = null,
    @Indexed(unique = true)
    var name: String,
    var originalName: String? = null,
    var paperback: Int? = null,
    @Indexed(unique = true)
    var authors: List<Author>? = emptyList(),
    @Indexed(unique = true)
    var genres: List<String>? = emptyList(),
    @DBRef
    var user: User? = null,
    var comments: List<Comment>? = emptyList()
)