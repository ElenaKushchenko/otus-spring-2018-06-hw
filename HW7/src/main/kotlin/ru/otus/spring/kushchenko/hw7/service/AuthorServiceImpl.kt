package ru.otus.spring.kushchenko.hw7.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.otus.spring.kushchenko.hw7.entity.Author
import ru.otus.spring.kushchenko.hw7.repository.AuthorRepository
import java.lang.IllegalArgumentException

/**
 * Created by Елена on Июль, 2018
 */
@Service
@Transactional
class AuthorServiceImpl(private val authorRepository: AuthorRepository) : AuthorService {
    override fun getAll(): List<Author> = authorRepository.findAll()

    override fun get(id: Int): Author = authorRepository.findById(id)
        .orElseThrow { IllegalArgumentException("Author with id = $id not found") }

    override fun create(author: Author) = authorRepository.save(author)

    override fun update(author: Author): Author {
        val id = author.id!!

        if (authorRepository.existsById(id).not())
            throw IllegalArgumentException("Author with id = $id not found")

        return authorRepository.save(author)
    }

    override fun delete(id: Int) = authorRepository.deleteById(id)
}