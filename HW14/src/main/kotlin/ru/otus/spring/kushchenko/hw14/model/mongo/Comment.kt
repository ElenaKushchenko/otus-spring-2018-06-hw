package ru.otus.spring.kushchenko.hw14.model.mongo

import java.time.LocalDateTime

/**
 * Created by Елена on Авг., 2018
 */
data class Comment(
    val text: String,
    val date: LocalDateTime? = LocalDateTime.now(),
    var username: String
)