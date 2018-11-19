package ru.otus.spring.kushchenko.hw13.service

import org.springframework.security.access.prepost.PreAuthorize

/**
 * Created by Елена on Июль, 2018
 */
@PreAuthorize("isAuthenticated()")
interface AuthorService {
    fun getAll(): List<String>
}