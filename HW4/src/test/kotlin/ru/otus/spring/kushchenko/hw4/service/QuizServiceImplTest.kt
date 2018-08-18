package ru.otus.spring.kushchenko.hw4.service

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.*
import ru.otus.spring.kushchenko.hw4.model.Question
import ru.otus.spring.kushchenko.hw4.repository.QuestionRepository

/**
 * Created by Елена on Июль, 2018
 */
class QuizServiceImplTest {
    private lateinit var repository: QuestionRepository
    private lateinit var service: QuizService

    private val expQuestion = Question(
            id = 1,
            text = "Wow!",
            correctAnswerNum = 1,
            answers = listOf("Such Junit", "Very Mockito", "Many tests")
    )
    private val expQuestionDto = expQuestion.toDto()

    @BeforeEach
    fun setup() {
        repository = mock()
        service = QuizServiceImpl(repository)
    }

    @Nested
    @DisplayName("Tests for the method getQuestion()")
    inner class GetQuestion {
        @Test
        fun shouldReturnNullWhenQuestionNotExists() {
            whenever(repository.getById(any())).thenReturn(null)
            val actualQuestion = service.getQuestion(any())
            verify(repository).getById(any())
            assertThat(actualQuestion).isNull()
        }

        @Test
        fun shouldReturnQuestionWhenExists() {
            whenever(repository.getById(expQuestion.id)).thenReturn(expQuestion)
            val actualQuestion = service.getQuestion(expQuestion.id)
            verify(repository).getById(expQuestion.id)
            assertThat(actualQuestion).isEqualTo(expQuestionDto)
        }
    }

    @Test
    fun getQuestions() {
        whenever(repository.getAll()).thenReturn(listOf(expQuestion))
        val actualQuestions = service.getQuestions()
        verify(repository).getAll()
        assertThat(actualQuestions).containsOnly(expQuestionDto)
    }

    @Nested
    @DisplayName("Tests for the method checkAnswer()")
    inner class CheckAnswer {
        @Test
        fun shouldReturnTrueWhenAnswerIsRight() {
            whenever(repository.getById(expQuestion.id)).thenReturn(expQuestion)
            val actualResult = service.checkAnswer(expQuestion.id, expQuestion.correctAnswerNum)
            verify(repository).getById(expQuestion.id)
            assertThat(actualResult).isTrue()
        }

        @Test
        fun shouldReturnFalseWhenAnswerIsWrong() {
            whenever(repository.getById(expQuestion.id)).thenReturn(expQuestion)
            val actualResult = service.checkAnswer(expQuestion.id, expQuestion.correctAnswerNum + 1)
            verify(repository).getById(expQuestion.id)
            assertThat(actualResult).isFalse()
        }

        @Test
        fun shouldFallWhenQuestionWithSpecifiedIdIsNotExists() {
            whenever(repository.getById(any())).thenReturn(null)
            assertThatThrownBy { service.checkAnswer(100500, 100500) }
                    .isInstanceOf(NoSuchElementException::class.java)
        }
    }

    @Nested
    @DisplayName("Tests for the method getCorrectAnswer()")
    inner class GetCorrectAnswer {
        @Test
        fun shouldReturnAnswerWhenQuestionWithSpecifiedIdIsExists() {
            whenever(repository.getById(expQuestion.id)).thenReturn(expQuestion)
            val actualResult = service.getCorrectAnswer(expQuestion.id)
            verify(repository).getById(expQuestion.id)
            assertThat(actualResult).isEqualTo(expQuestion.correctAnswerNum)
        }

        @Test
        fun shouldFallWhenQuestionWithSpecifiedIdIsNotExists() {
            whenever(repository.getById(any())).thenReturn(null)
            assertThatThrownBy { service.getCorrectAnswer(100500) }
                    .isInstanceOf(NoSuchElementException::class.java)
        }
    }
}