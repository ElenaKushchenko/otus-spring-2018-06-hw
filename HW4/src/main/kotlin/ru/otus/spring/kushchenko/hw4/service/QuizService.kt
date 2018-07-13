package ru.otus.spring.kushchenko.hw4.service

import ru.otus.spring.kushchenko.hw4.model.dto.QuestionDto

/**
 * Created by Елена on Июль, 2018
 */
interface QuizService {
    fun getQuestion(id: Int): QuestionDto?
    fun getQuestions(): List<QuestionDto>
    fun checkAnswer(questionId: Int, answerId: Int): Boolean
    fun getCorrectAnswer(questionId: Int): Int
}