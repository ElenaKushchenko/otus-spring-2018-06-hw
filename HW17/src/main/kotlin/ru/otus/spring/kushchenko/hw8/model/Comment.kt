package ru.otus.spring.kushchenko.hw8.model

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

/**
 * Created by Елена on Авг., 2018
 */
@Document(collection = "comments")
data class Comment(
    @Id
    val id: String? = null,
    val text: String,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val date: LocalDateTime? = LocalDateTime.now(),
    @DBRef
    var user: User,
    @DBRef
    var book: Book
)