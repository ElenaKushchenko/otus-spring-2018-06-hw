package ru.otus.spring.kushchenko.hw8.service

import org.springframework.stereotype.Service
import ru.otus.spring.kushchenko.hw8.repository.GenreRepository

@Service
class GenreServiceImpl(private val genreRepository: GenreRepository) : GenreService {
    override fun getAll(): List<String> = genreRepository.getAll()
}