package ru.otus.spring.kushchenko.hw1.model.dto

data class QuestionDto(
        val id: Int,
        val question: String,
        val answers: List<String>
)