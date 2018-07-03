package ru.otus.spring.kushchenko.hw1.repository

import ru.otus.spring.kushchenko.hw1.model.Question

/**
 * Created by Елена on Июль, 2018
 */
interface QuestionRepository {
    fun getAll(): List<Question>
}