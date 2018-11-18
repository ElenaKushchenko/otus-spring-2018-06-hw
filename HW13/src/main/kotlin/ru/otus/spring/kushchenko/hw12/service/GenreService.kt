package ru.otus.spring.kushchenko.hw12.service

import org.springframework.security.access.prepost.PreAuthorize

/**
 * Created by Елена on Июль, 2018
 */
@PreAuthorize("isAuthenticated()")
interface GenreService {
    fun getAll(): List<String>
}