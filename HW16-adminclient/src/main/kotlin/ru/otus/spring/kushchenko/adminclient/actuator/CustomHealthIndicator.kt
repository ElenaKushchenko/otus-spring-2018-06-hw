package ru.otus.spring.kushchenko.adminclient.actuator

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.health.AbstractHealthIndicator
import org.springframework.boot.actuate.health.Health
import org.springframework.stereotype.Component

@Component
class CustomHealthIndicator(@Value("\${app.healthy}") private val isHealthy: Boolean): AbstractHealthIndicator() {

    override fun doHealthCheck(builder: Health.Builder) {
        if (isHealthy)
            builder.up()
        else
            builder.down()
    }
}