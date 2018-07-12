package ru.otus.spring.kushchenko.hw2

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import ru.otus.spring.kushchenko.hw2.controller.QuizController

/**
 * Created by Елена on Июль, 2018
 */
@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
class Main

fun main(args: Array<String>) {
    val context = AnnotationConfigApplicationContext(Main::class.java)
    val controller = context.getBean(QuizController::class.java)
    controller.startQuiz()
}