package ru.otus.spring.kushchenko.hw4.repository

import ru.otus.spring.kushchenko.hw4.model.Question

/**
 * Created by Елена on Июль, 2018
 */
interface QuestionRepository {
    fun getById(id: Int): Question?
    fun getAll(): List<Question>
}