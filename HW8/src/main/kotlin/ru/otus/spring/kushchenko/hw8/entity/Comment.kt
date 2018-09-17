package ru.otus.spring.kushchenko.hw8.entity

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

/**
 * Created by Елена on Авг., 2018
 */
data class Comment(
    val id: Int? = null,
    var text: String,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val date: LocalDateTime? = LocalDateTime.now(),
    val user: User,
    var bookId: Int
)