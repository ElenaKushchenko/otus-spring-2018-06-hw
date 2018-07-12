package ru.otus.spring.kushchenko.hw3.model.dto

data class QuestionDto(
        val id: Int,
        val text: String,
        val answers: List<String>
)