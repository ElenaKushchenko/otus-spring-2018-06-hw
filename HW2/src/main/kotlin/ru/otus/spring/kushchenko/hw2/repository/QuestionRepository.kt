package ru.otus.spring.kushchenko.hw2.repository

import ru.otus.spring.kushchenko.hw2.model.Question

/**
 * Created by Елена on Июль, 2018
 */
interface QuestionRepository {
    fun getById(id: Int): Question?
    fun getAll(): List<Question>
}