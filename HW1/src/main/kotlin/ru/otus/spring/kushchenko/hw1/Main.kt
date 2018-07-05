package ru.otus.spring.kushchenko.hw1

import org.springframework.context.support.ClassPathXmlApplicationContext
import ru.otus.spring.kushchenko.hw1.service.QuizService

/**
 * Created by Елена on Июль, 2018
 */
fun main(args: Array<String>) {
    val context = ClassPathXmlApplicationContext("spring.xml")
    val quizService = context.getBean(QuizService::class.java)

    println("Welcome!")
    println("Please introduce yourself")
    val userName = readLine()

    println("Start quiz? (y/n)")
    while (readLine() == "y") {
        val questions = quizService.getQuestions()
        var result = 0

        questions.forEachIndexed { i, question ->
            println("Question ${i + 1}/${questions.size}")
            println(question.question)
            question.answers.forEachIndexed { j, answer -> println("${j + 1}. $answer") }

            var answer: Int?
            do {
                print("Your answer is: ")
                answer = readLine()?.toIntOrNull()
            } while (answer == null)

            if (quizService.checkAnswer(question.id, answer)) {
                println("You are right")
                result++
            } else {
                val correctAnswer = quizService.getCorrectAnswer(question.id)
                println("You are wrong")
                println("Correct answer is: $correctAnswer")
            }
        }

        println("$userName, your result is $result/${questions.size}")
        println("Try again? (y/n)")
    }

    println("Bye!")
}