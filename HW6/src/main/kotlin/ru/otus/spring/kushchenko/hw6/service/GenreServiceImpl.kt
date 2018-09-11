package ru.otus.spring.kushchenko.hw6.service

import org.springframework.stereotype.Service
import ru.otus.spring.kushchenko.hw6.entity.Genre
import ru.otus.spring.kushchenko.hw6.repository.GenreRepository

/**
 * Created by Елена on Июль, 2018
 */
@Service
class GenreServiceImpl(private val genreRepository: GenreRepository) : GenreService {
    override fun getAll() = genreRepository.getAll()

    override fun get(id: Int) = genreRepository.get(id)

    override fun create(genre: Genre) = genreRepository.create(genre)

    override fun update(id: Int, genre: Genre) = genreRepository.update(id, genre)

    override fun delete(id: Int) = genreRepository.delete(id)
}