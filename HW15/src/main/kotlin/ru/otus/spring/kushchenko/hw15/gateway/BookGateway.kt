package ru.otus.spring.kushchenko.hw15.gateway

import org.springframework.integration.annotation.Gateway
import org.springframework.integration.annotation.MessagingGateway
import org.springframework.messaging.Message
import ru.otus.spring.kushchenko.hw15.model.ShortBook

@MessagingGateway
interface BookGateway {
    @Gateway(requestChannel = "getAllBooksInputChannel", replyChannel = "getAllBooksOutputChannel")
    fun getAll(message: Message<*>): List<ShortBook>
}
