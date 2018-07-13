package ru.otus.spring.kushchenko.hw4.controller

import org.springframework.context.MessageSource
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import ru.otus.spring.kushchenko.hw4.service.QuizService
import ru.otus.spring.kushchenko.hw4.util.Util.notBlankOrDefault
import java.util.*

/**
 * Created by Елена on Июль, 2018
 */
@ShellComponent
class QuizController(private val quizService: QuizService,
                     private val messageSource: MessageSource,
                     private val locale: Locale) {

    @ShellMethod(value = "Start playing the quiz", key = ["start-quiz"])
    fun startQuiz() {
        println(getMessage("greeting"))
        println(getMessage("acquaintance"))
        val userName = readLine().notBlankOrDefault(getMessage("default-username"))

        println(getMessage("start-confirmation"))
        while (readLine() == getMessage("accept-letter")) {
            val questions = quizService.getQuestions()
            var result = 0

            questions.forEachIndexed { i, question ->
                println(getMessage("question-header", i + 1, questions.size))
                println(question.text)
                question.answers.forEachIndexed { j, answer -> println("${j + 1}. $answer") }

                var answer: Int?
                do {
                    println(getMessage("answer-invitation"))
                    answer = readLine()?.toIntOrNull()
                } while (answer == null)

                if (quizService.checkAnswer(question.id, answer)) {
                    println(getMessage("right-answer"))
                    result++
                } else {
                    val correctAnswer = quizService.getCorrectAnswer(question.id)
                    println(getMessage("wrong-answer"))
                    println(getMessage("correct-answer", correctAnswer))
                }
            }

            println(getMessage("result", userName!!, result, questions.size))
            println(getMessage("repeat-confirmation"))
        }

        println(getMessage("farewell"))
    }

    private fun getMessage(key: String, vararg args: Any) =
            messageSource.getMessage(key, args, locale)
}