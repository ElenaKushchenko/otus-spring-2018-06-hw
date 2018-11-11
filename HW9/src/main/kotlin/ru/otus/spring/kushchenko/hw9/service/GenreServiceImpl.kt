package ru.otus.spring.kushchenko.hw9.service

import org.springframework.stereotype.Service
import ru.otus.spring.kushchenko.hw9.repository.GenreRepository

@Service
class GenreServiceImpl(private val repository: GenreRepository) : GenreService {
    override fun getAll(): List<String> = repository.getAll()
}