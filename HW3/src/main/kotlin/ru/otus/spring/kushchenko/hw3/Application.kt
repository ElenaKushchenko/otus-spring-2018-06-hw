package ru.otus.spring.kushchenko.hw3

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import ru.otus.spring.kushchenko.hw3.controller.QuizController

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    val context = runApplication<Application>(*args)
    val controller = context.getBean(QuizController::class.java)
    controller.startQuiz()
}
