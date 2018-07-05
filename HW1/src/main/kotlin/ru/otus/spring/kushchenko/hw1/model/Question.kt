package ru.otus.spring.kushchenko.hw1.model

import ru.otus.spring.kushchenko.hw1.model.dto.QuestionDto

/**
 * Created by Елена on Июль, 2018
 */
data class Question(
        val id: Int,
        val question: String,
        val correctAnswerNum: Int,
        val answers: List<String>
) {
    fun toDto() = QuestionDto(
            id = id,
            question = question,
            answers = answers
    )
}