package ru.otus.spring.kushchenko.hw6.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

/**
 * Created by Елена on Авг., 2018
 */
data class CommentRequest(
    var text: String,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val date: LocalDateTime? = null,
    val userId: Int,
    val bookId: Int
)