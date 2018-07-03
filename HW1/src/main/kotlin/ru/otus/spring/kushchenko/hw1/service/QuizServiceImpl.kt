package ru.otus.spring.kushchenko.hw1.service

import org.slf4j.LoggerFactory
import ru.otus.spring.kushchenko.hw1.model.Question
import ru.otus.spring.kushchenko.hw1.repository.QuestionRepository

/**
 * Created by Елена on Июль, 2018
 */
class QuizServiceImpl(private val questionRepository: QuestionRepository): QuizService {
    private val log = LoggerFactory.getLogger(QuizServiceImpl::class.java)

    override fun getQuestions(): List<Question>
        = questionRepository.getAll()

    override fun validateAnswers(answerMap: Map<Int, Int>): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}