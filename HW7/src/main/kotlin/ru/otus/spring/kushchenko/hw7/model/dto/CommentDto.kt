package ru.otus.spring.kushchenko.hw7.model.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

/**
 * Created by Елена on Авг., 2018
 */
data class CommentDto(
    val id: Int? = null,
    var text: String? = null,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val date: LocalDateTime? = null,
    val userId: Int? = null,
    val bookId: Int? = null
)