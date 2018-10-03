package ru.otus.spring.kushchenko.hw8.service

import org.springframework.stereotype.Service
import ru.otus.spring.kushchenko.hw8.model.Author
import ru.otus.spring.kushchenko.hw8.repository.AuthorRepository

/**
 * Created by Елена on Июль, 2018
 */
@Service
class AuthorServiceImpl(private val repository: AuthorRepository) : AuthorService {
    override fun getAll(): List<Author> = repository.getAll()
}