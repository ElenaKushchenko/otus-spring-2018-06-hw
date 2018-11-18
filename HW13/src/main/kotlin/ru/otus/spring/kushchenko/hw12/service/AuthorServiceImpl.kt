package ru.otus.spring.kushchenko.hw12.service

import org.springframework.stereotype.Service
import ru.otus.spring.kushchenko.hw12.repository.AuthorRepository

/**
 * Created by Елена on Июль, 2018
 */
@Service
class AuthorServiceImpl(private val repository: AuthorRepository) : AuthorService {
    override fun getAll(): List<String> = repository.getAll()
}