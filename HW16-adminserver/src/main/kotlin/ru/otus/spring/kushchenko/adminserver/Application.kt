package ru.otus.spring.kushchenko.adminserver

import de.codecentric.boot.admin.server.config.EnableAdminServer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableAdminServer
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}