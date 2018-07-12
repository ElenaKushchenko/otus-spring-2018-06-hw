package ru.otus.spring.kushchenko.hw3.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.otus.spring.kushchenko.hw3.model.dto.QuestionDto
import ru.otus.spring.kushchenko.hw3.repository.QuestionRepository

/**
 * Created by Елена on Июль, 2018
 */
@Service
class QuizServiceImpl(private val repository: QuestionRepository) : QuizService {
    private val log = LoggerFactory.getLogger(QuizServiceImpl::class.java)

    override fun getQuestion(id: Int): QuestionDto? {
        log.debug("Get question with id = $id")
        val question = repository.getById(id)
        return if (question != null) {
            question.toDto()
        } else {
            log.error("Question with id = $id not found")
            null
        }
    }

    override fun getQuestions(): List<QuestionDto> {
        log.debug("Get all questions")
        val questions = repository.getAll()
        return questions.map { it.toDto() }
    }

    override fun checkAnswer(questionId: Int, answerId: Int): Boolean {
        log.debug("Check answer: question = $questionId, answer = $answerId")
        val question = repository.getById(questionId)
        return if (question != null) {
            question.correctAnswerNum == answerId
        } else {
            throw NoSuchElementException("Question with id = $questionId not found")
        }
    }

    override fun getCorrectAnswer(questionId: Int): Int {
        log.debug("Get correct answer for question with id = $questionId")
        val question = repository.getById(questionId)
        return question?.correctAnswerNum ?: throw NoSuchElementException("Question with id = $questionId not found")
    }
}