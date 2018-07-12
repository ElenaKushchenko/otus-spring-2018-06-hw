package ru.otus.spring.kushchenko.hw3.repository

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import ru.otus.spring.kushchenko.hw3.model.Question
import java.io.FileNotFoundException

/**
 * Created by Елена on Июль, 2018
 */
class CsvQuestionRepositoryTest {
    private val expQuestion1 = Question(
            id = 1,
            text = "На какой планете живут гунганы?",
            correctAnswerNum = 2,
            answers = listOf("Мустафар", "Набу", "Хот", "Дагоба")
    )
    private val expQuestion2 = Question(
            id = 2,
            text = "Кто собрал C-3PO?",
            correctAnswerNum = 4,
            answers = listOf("Хан Соло", "Оби-Ван Кеноби", "Чубакка", "Энакин Скайуокер")
    )

    @Test
    fun shouldFallWhenFileIsNotExists() {
        val repository = CsvQuestionRepository("classpath:data/failure.csv")
        assertThatThrownBy { repository.getAll() }
                .isInstanceOf(FileNotFoundException::class.java)
    }

    @Test
    fun shouldFallWhenFileHasInvalidFormat() {
        val repository = CsvQuestionRepository("classpath:data/invalid-csv-file.csv")
        assertThatThrownBy { repository.getAll() }
                .isInstanceOf(RuntimeException::class.java)
    }

    @Test
    fun shouldFallWhenFileContainsUnexpectedData() {
        val repository = CsvQuestionRepository("classpath:data/non-questions-file.csv")
        assertThatThrownBy { repository.getAll() }
                .isInstanceOfAny(ArrayIndexOutOfBoundsException::class.java,
                        NumberFormatException::class.java)
    }

    @Nested
    @DisplayName("Tests for the method getById()")
    inner class GetById {

        @Test
        fun shouldReturnQuestionWhenSpecifiedIdIsExists() {
            val repository = CsvQuestionRepository("classpath:data/valid-questions.csv")
            val actualQuestion = repository.getById(expQuestion1.id)
            assertThat(actualQuestion).isEqualTo(expQuestion1)
        }

        @Test
        fun shouldReturnNullWhenSpecifiedIdIsNotExists() {
            val repository = CsvQuestionRepository("classpath:data/valid-questions.csv")
            val actualQuestion = repository.getById(100500)
            assertThat(actualQuestion).isNull()
        }
    }

    @Test
    fun shouldReturnQuestions() {
        val repository = CsvQuestionRepository("classpath:data/valid-questions.csv")
        val actualQuestions = repository.getAll()
        assertThat(actualQuestions).hasSize(2).contains(expQuestion1, expQuestion2)
    }
}