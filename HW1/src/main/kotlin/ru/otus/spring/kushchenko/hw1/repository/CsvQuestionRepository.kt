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

    private enum class QuestionSchema(val position: Int) {
        ID(0),
        QUESTION(1),
        CORRECT_ANSWER(2),
        ANSWERS(3)
    }

    override fun getAll(): List<Question> {
        ResourceUtils.getFile(dataSource).reader().use { reader ->
            return CSVReader(reader).use { csvReader ->
                csvReader.map {
                    log.debug("Parse row: $it")
                    Question(
                            id = it[QuestionSchema.ID.position].toInt(),
                            question = it[QuestionSchema.QUESTION.position],
                            correctAnswerNum = it[QuestionSchema.CORRECT_ANSWER.position].toInt(),
                            answers = it.slice(QuestionSchema.ANSWERS.position..it.size).filterNotNull()
                    )
                }
            }
        }
    }
}