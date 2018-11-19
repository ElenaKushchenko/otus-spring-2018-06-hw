package ru.otus.spring.kushchenko.hw18.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Created by Елена on Июль, 2018
 */
@Document(collection = "users")
data class User(
    @Id
    val id: String? = null,
    @Indexed(unique = true)
    val name: String
)