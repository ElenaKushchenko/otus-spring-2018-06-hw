package ru.otus.spring.kushchenko.hw14

import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import ru.otus.spring.kushchenko.hw14.service.BookMigrationService

@Component
class ApplicationListener(private val bookMigrationService: BookMigrationService) {

    @EventListener(ApplicationStartedEvent::class)
    fun onApplicationStartedEvent() {
        bookMigrationService.migrateFromJpaToMongo()
    }
}