package ru.otus.spring.kushchenko.hw9.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

/**
 * Created by Елена on Авг., 2018
 */
data class Comment(
    val text: String,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val date: LocalDateTime? = LocalDateTime.now(),
    var username: String
)