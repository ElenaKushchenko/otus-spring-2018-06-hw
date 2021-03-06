package ru.otus.spring.kushchenko.hw7.service

import org.springframework.stereotype.Service
import ru.otus.spring.kushchenko.hw7.model.Genre
import ru.otus.spring.kushchenko.hw7.repository.GenreRepository

/**
 * Created by Елена on Июль, 2018
 */
@Service
class GenreServiceImpl(private val genreRepository: GenreRepository) : GenreService {
    override fun getAll(): List<Genre> = genreRepository.findAll()

    override fun get(id: Int): Genre = genreRepository.findById(id)
        .orElseThrow { IllegalArgumentException("Genre with id = $id not found") }

    override fun create(genre: Genre) = genreRepository.save(genre)

    override fun update(genre: Genre): Genre {
        val id = genre.id!!

        if (genreRepository.existsById(id).not())
            throw IllegalArgumentException("Genre with id = $id not found")

        return genreRepository.save(genre)
    }

    override fun delete(id: Int) = genreRepository.deleteById(id)
}