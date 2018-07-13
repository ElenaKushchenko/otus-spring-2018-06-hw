package ru.otus.spring.kushchenko.hw4.repository

import com.opencsv.CSVReader
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Repository
import org.springframework.util.ResourceUtils
import ru.otus.spring.kushchenko.hw4.model.Question

/**
 * Created by Елена on Июль, 2018
 */
@Repository
class CsvQuestionRepository(@Value("\${questionSource}") private val questionSource: String) : QuestionRepository {
    private val log = LoggerFactory.getLogger(CsvQuestionRepository::class.java)

    @Cacheable("questions")
    override fun getById(id: Int): Question? {
        ResourceUtils.getFile(questionSource).reader().use { reader ->
            return CSVReader(reader).use { csvReader ->
                csvReader.find { hasId(it, id) }?.let { parseQuestion(it) }
            }
        }
    }

    @Cacheable("questions")
    override fun getAll(): List<Question> {
        ResourceUtils.getFile(questionSource).reader().use { reader ->
            return CSVReader(reader).use { csvReader ->
                csvReader.map { parseQuestion(it) }
            }
        }
    }

    private fun parseQuestion(row: Array<String>): Question {
        log.debug("Parse row: $row")
        return Question(
                id = row[QuestionSchema.ID.ordinal].toInt(),
                text = row[QuestionSchema.QUESTION.ordinal],
                correctAnswerNum = row[QuestionSchema.CORRECT_ANSWER.ordinal].toInt(),
                answers = row.slice(QuestionSchema.ANSWERS.ordinal until row.size).filter { it.isNotBlank() }
        )
    }

    private fun hasId(row: Array<String>, id: Int) = row[QuestionSchema.ID.ordinal].toInt() == id

    private enum class QuestionSchema {
        ID,
        QUESTION,
        CORRECT_ANSWER,
        ANSWERS
    }
}