package ru.otus.spring.kushchenko.hw6.service

import org.springframework.stereotype.Service
import ru.otus.spring.kushchenko.hw6.entity.Author
import ru.otus.spring.kushchenko.hw6.repository.AuthorRepository

/**
 * Created by Елена on Июль, 2018
 */
@Service
class AuthorServiceImpl(private val authorRepository: AuthorRepository) : AuthorService {
    override fun getAll() = authorRepository.getAll()

    override fun get(id: Int) = authorRepository.get(id)

    override fun create(author: Author) = authorRepository.create(author)

    override fun update(id: Int, author: Author) = authorRepository.update(id, author)

    override fun delete(id: Int) = authorRepository.delete(id)
}