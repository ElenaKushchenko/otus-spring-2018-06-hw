package ru.otus.spring.kushchenko.hw11.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import ru.otus.spring.kushchenko.hw11.repository.GenreRepository

@Service
class GenreServiceImpl(private val repository: GenreRepository) : GenreService {
    override fun getAll(): Flux<String> = repository.getAll()
}