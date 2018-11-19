package ru.otus.spring.kushchenko.hw15.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.annotation.IntegrationComponentScan
import org.springframework.integration.dsl.IntegrationFlow
import org.springframework.integration.dsl.IntegrationFlows
import org.springframework.integration.dsl.MessageChannels
import org.springframework.messaging.MessageChannel

@Configuration
@IntegrationComponentScan
class IntegrationConfig {

    @Bean
    fun getAllBooksInputChannel() = MessageChannels.publishSubscribe().get()

    @Bean
    fun getAllBooksOutputChannel() = MessageChannels.publishSubscribe().get()

    @Bean
    fun getAllBooksFlow() =
        IntegrationFlows.from("getAllBooksInputChannel")
            .log()
            .handle("bookServiceImpl", "getAll")
            .log()
            .channel("getAllBooksOutputChannel")
            .get()
}
