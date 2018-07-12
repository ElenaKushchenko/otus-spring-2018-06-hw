package ru.otus.spring.kushchenko.hw3.repository

import ru.otus.spring.kushchenko.hw3.model.Question

/**
 * Created by Елена on Июль, 2018
 */
interface QuestionRepository {
    fun getById(id: Int): Question?
    fun getAll(): List<Question>
}