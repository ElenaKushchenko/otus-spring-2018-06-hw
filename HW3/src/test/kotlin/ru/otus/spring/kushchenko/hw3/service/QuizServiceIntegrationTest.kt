package ru.otus.spring.kushchenko.hw3.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.context.junit.jupiter.SpringExtension
import ru.otus.spring.kushchenko.hw3.model.dto.QuestionDto
import ru.otus.spring.kushchenko.hw3.repository.CsvQuestionRepository
import ru.otus.spring.kushchenko.hw3.repository.QuestionRepository

/**
 * Created by Елена on Июль, 2018
 */
@SpringBootTest
@ExtendWith(SpringExtension::class)
class QuizServiceIntegrationTest {

    @TestConfiguration
    class QuizServiceIntegrationTestConfiguration {

        @Bean
        fun questionRepository() =
                CsvQuestionRepository("classpath:data/integration-test-questions.csv")

        @Bean
        fun quizServiceImpl(questionRepository: QuestionRepository) =
                QuizServiceImpl(questionRepository)
    }

    @Autowired
    private lateinit var quizService: QuizService

    @Test
    fun getQuestions() {
        val expQuestions = listOf(
                QuestionDto(1, "Основное оружие рыцаря-джедая - это...", listOf("Бластер", "Волшебная палочка", "Кольцо всевластия", "Световой меч")),
                QuestionDto(2, "Какое название носит корабль Хана Соло?", listOf("Звездный разрушитель", "Столетний орел", "Тысячелетний сокол", "Многовековая сова")),
                QuestionDto(3, "На какой планете живут гунганы?", listOf("Мустафар", "Набу", "Хот", "Дагоба")),
                QuestionDto(4, "Кто собрал C-3PO?", listOf("Хан Соло", "Оби-Ван Кеноби", "Чубакка", "Энакин Скайуокер")),
                QuestionDto(5, "Сколько лет на свете мастер Йода прожил, спросить хочу я?", listOf("900","800","700","1024"))
        )
        val actualQuestions = quizService.getQuestions()

        assertThat(actualQuestions).containsAll(expQuestions)
    }
}