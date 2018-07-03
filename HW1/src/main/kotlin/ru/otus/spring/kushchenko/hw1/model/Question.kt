package ru.otus.spring.kushchenko.hw1.model

/**
 * Created by Елена on Июль, 2018
 */
data class Question(
        val id: Int,
        val question: String,
        val correctAnswerNum: Int,
        val answers: List<String>
)