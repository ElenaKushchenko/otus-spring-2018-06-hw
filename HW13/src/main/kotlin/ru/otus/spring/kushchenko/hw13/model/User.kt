package ru.otus.spring.kushchenko.hw13.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
 * Created by Елена on Нояб., 2018
 */
@Document(collection = "users")
data class User(
    @Id
    val id: String? = null,
    val username: String,
    val password: String,
    val roles: List<UserRole>
)