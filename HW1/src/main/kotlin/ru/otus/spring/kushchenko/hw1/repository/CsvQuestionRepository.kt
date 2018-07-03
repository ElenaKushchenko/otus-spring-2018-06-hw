package ru.otus.spring.kushchenko.hw1.repository

import com.opencsv.CSVReader
import org.slf4j.LoggerFactory
import org.springframework.util.ResourceUtils
import ru.otus.spring.kushchenko.hw1.model.Question

/**
 * Created by Елена on Июль, 2018
 */
class CsvQuestionRepository(private val dataSource: String) : QuestionRepository {
    private val log = LoggerFactory.getLogger(CsvQuestionRepository::class.java)

    override fun getById(id: Int): Question? {
        ResourceUtils.getFile(dataSource).reader().use { reader ->
            return CSVReader(reader).use { csvReader ->
                csvReader.find { hasId(it, id) }?.let { parseQuestion(it) }
            }
        }
    }

    override fun getAll(): List<Question> {
        ResourceUtils.getFile(dataSource).reader().use { reader ->
            return CSVReader(reader).use { csvReader ->
                csvReader.mapNotNull { parseQuestion(it) }
            }
        }
    }

    private fun parseQuestion(row: Array<String>): Question? {
        log.debug("Parse row: $row")
        return try {
            Question(
                    id = row[QuestionSchema.ID.position].toInt(),
                    question = row[QuestionSchema.QUESTION.position],
                    correctAnswerNum = row[QuestionSchema.CORRECT_ANSWER.position].toInt(),
                    answers = row.slice(QuestionSchema.ANSWERS.position until row.size).filter { it.isNotBlank() }
            )
        } catch (ex: Exception) {
            log.error("Error occurred while trying to parse row: $row", ex)
            null
        }
    }

    private fun hasId(row: Array<String>, id: Int) = row[QuestionSchema.ID.position].toInt() == id

    private enum class QuestionSchema(val position: Int) {
        ID(0),
        QUESTION(1),
        CORRECT_ANSWER(2),
        ANSWERS(3)
    }
}