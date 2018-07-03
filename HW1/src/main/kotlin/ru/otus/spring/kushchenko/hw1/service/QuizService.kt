package ru.otus.spring.kushchenko.hw1.service

import ru.otus.spring.kushchenko.hw1.model.Question

/**
 * Created by Елена on Июль, 2018
 */
interface QuizService {
    fun getQuestions(): List<Question>
    fun validateAnswers(answerMap: Map<Int, Int>): Int
}