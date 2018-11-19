package ru.otus.spring.kushchenko.hw18.model

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
    val name: String,
    val originalName: String? = null,
    val paperback: Int? = null,
    val authors: List<Author>? = emptyList(),
    val genres: List<String>? = emptyList(),
    @DBRef
    var user: User? = null
)