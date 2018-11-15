package ru.otus.spring.kushchenko.hw11.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import ru.otus.spring.kushchenko.hw11.model.Author
import ru.otus.spring.kushchenko.hw11.repository.AuthorRepository

/**
 * Created by Елена on Июль, 2018
 */
@Service
class AuthorServiceImpl(private val repository: AuthorRepository) : AuthorService {
    override fun getAll(): Flux<Author> = repository.getAll()
}