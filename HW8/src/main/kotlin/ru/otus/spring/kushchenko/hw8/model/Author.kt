package ru.otus.spring.kushchenko.hw8.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class Author(
    val name: String,
    val country: String? = null,
    @JsonFormat(pattern = "yyyy-MM-dd")
    val dayOfBirth: LocalDate? = null
)