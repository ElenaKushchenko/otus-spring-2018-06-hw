package ru.otus.spring.kushchenko.hw1

import org.springframework.context.support.ClassPathXmlApplicationContext
import ru.otus.spring.kushchenko.hw1.repository.QuestionRepository

/**
 * Created by Елена on Июль, 2018
 */
fun main(args: Array<String>) {
    val context = ClassPathXmlApplicationContext("spring.xml")
    val questionRepository = context.getBean(QuestionRepository::class.java)

    println("Welcome!")
    println("Представьтесь пожалуйста")
    val userName = readLine()

    println("Начать тест? (y/n)")
    if (readLine() == "y") {
        val questions = questionRepository.getAll()
        questions.forEachIndexed { ix, element ->
            println("Вопрос $ix/${questions.size}")
            println(element.question)
            element.answers.forEachIndexed { ix, element -> println("$ix. $element") }
            print("Ваш ответ: ")
            val answer = readLine()
        }

    } else {
        println("Выход...")
    }
}