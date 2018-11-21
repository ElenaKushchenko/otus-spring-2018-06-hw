package ru.otus.spring.kushchenko.hw15.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.annotation.IntegrationComponentScan
import org.springframework.integration.dsl.IntegrationFlows
import org.springframework.integration.dsl.MessageChannels
import ru.otus.spring.kushchenko.hw15.model.Book
import ru.otus.spring.kushchenko.hw15.model.Status

@Configuration
@IntegrationComponentScan
class IntegrationConfig {

    @Bean
    fun getAllBooksInputChannel() = MessageChannels.publishSubscribe().get()

    @Bean
    fun getAllBooksOutputChannel() = MessageChannels.publishSubscribe().get()


    @Bean
    fun popularBooksChannel() = MessageChannels.publishSubscribe().get()

    @Bean
    fun unpopularBooksChannel() = MessageChannels.publishSubscribe().get()

    @Bean
    fun booksAggregatorChannel() = MessageChannels.publishSubscribe().get()


    @Bean
    fun getAllBooksFlow() =
        IntegrationFlows.from("getAllBooksInputChannel")
            .handle("bookServiceImpl", "getAll")
            .split()
            .route(fun(book: Book): Boolean = book.comments.isNullOrEmpty().not(), { mapping ->
                mapping
                    .channelMapping(true, "popularBooksChannel")
                    .channelMapping(false, "unpopularBooksChannel")
            })
            .get()

    @Bean
    fun popularBooksSubflow() =
        IntegrationFlows.from("popularBooksChannel")
            .handle(fun(book: Book): Book = book.apply { status = Status.POPULAR })
            .channel("booksAggregatorChannel")
            .get()

    @Bean
    fun unpopularBooksSubflow() =
        IntegrationFlows.from("unpopularBooksChannel")
            .handle(fun(book: Book): Book = book.apply { status = Status.UNPOPULAR })
            .channel("booksAggregatorChannel")
            .get()

    @Bean
    fun booksAggregatorSubflow() =
        IntegrationFlows.from("booksAggregatorChannel")
            .aggregate()
            .channel("getAllBooksOutputChannel")
            .get()
}
