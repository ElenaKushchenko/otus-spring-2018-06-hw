package ru.otus.spring.kushchenko.hw1.service

import ru.otus.spring.kushchenko.hw1.model.dto.QuestionDto

/**
 * Created by Елена on Июль, 2018
 */
interface QuizService {
    fun getQuestion(id: Int): QuestionDto?
    fun getQuestions(): List<QuestionDto>
    fun checkAnswer(questionId: Int, answerNum: Int): Boolean
    fun getCorrectAnswer(questionId: Int): Int
}