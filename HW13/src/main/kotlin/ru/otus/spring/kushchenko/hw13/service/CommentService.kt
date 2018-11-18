package ru.otus.spring.kushchenko.hw13.service

import org.springframework.security.access.prepost.PreAuthorize
import ru.otus.spring.kushchenko.hw13.model.Comment

/**
 * Created by Елена on Июль, 2018
 */
@PreAuthorize("isAuthenticated()")
interface CommentService {
    @PreAuthorize("hasRole('USER')")
    fun create(bookId: String, comment: Comment): Comment
}