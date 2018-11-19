package ru.otus.spring.kushchenko.hw14.configuration

import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.data.RepositoryItemReader
import org.springframework.batch.item.data.RepositoryItemWriter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.Sort.Direction.ASC
import ru.otus.spring.kushchenko.hw14.repository.JpaBookRepository
import ru.otus.spring.kushchenko.hw14.repository.MongoBookRepository
import ru.otus.spring.kushchenko.hw14.model.jpa.Book as JpaBook
import ru.otus.spring.kushchenko.hw14.model.mongo.Book as MongoBook
import ru.otus.spring.kushchenko.hw14.model.mongo.Comment as MongoComment

@Configuration
@EnableBatchProcessing
class BatchConfig {

    @Bean
    fun mongoBookWriter(bookRepository: MongoBookRepository) =
        RepositoryItemWriter<MongoBook>().apply {
            setRepository(bookRepository)
            setMethodName("save")
        }

    @Bean
    fun jpaItemReader(bookRepository: JpaBookRepository) =
        RepositoryItemReader<JpaBook>().apply {
            setRepository(bookRepository)
            setMethodName("findAll")
            setSort(mapOf(Pair("name", ASC)))
        }

    @Bean
    fun bookMigrationStep1(
        builder: StepBuilderFactory,
        reader: ItemReader<JpaBook>,
        writer: ItemWriter<MongoBook>
    ) =
        builder.get("bookMigrationStep1")
            .chunk<JpaBook, MongoBook>(10)
            .reader(reader)
            .processor(ItemProcessor<JpaBook, MongoBook> {
                MongoBook(
                    name = it.name,
                    originalName = it.originalName,
                    paperback = it.paperback,
                    authors = it.authors?.map { it.name!! },
                    genres = it.genres?.map { it.name!! },
                    comments = it.comments?.map {
                        MongoComment(
                            text = it.text,
                            date = it.date,
                            username = it.user.name!!
                        )
                    }
                )
            })
            .writer(writer)
            .build()

    @Bean("bookFromJpaToMongo")
    fun bookMigrationJob(jobBuilderFactory: JobBuilderFactory, @Qualifier("bookMigrationStep1") step1: Step) =
        jobBuilderFactory.get("bookMigrationJob")
            .flow(step1)
            .end()
            .build()

}
