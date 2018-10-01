package ru.otus.spring.kushchenko.hw8.model

import java.time.LocalDate

data class Author(
    val name: String,
    val country: String? = null,
    val dayOfBirth: LocalDate? = null
)