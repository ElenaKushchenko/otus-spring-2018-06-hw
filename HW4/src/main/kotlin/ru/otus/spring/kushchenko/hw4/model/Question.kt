package ru.otus.spring.kushchenko.hw4.model

import ru.otus.spring.kushchenko.hw4.model.dto.QuestionDto

/**
 * Created by Елена on Июль, 2018
 */
data class Question(
        val id: Int,
        val text: String,
        val correctAnswerNum: Int,
        val answers: List<String>
) {
    fun toDto() = QuestionDto(
            id = id,
            text = text,
            answers = answers
    )
}