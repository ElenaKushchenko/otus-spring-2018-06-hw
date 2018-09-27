package ru.otus.spring.kushchenko.hw8.entity

import java.time.LocalDate

data class Author(
    val name: String,
    val country: String?,
    val dayOfBirth: LocalDate?
)