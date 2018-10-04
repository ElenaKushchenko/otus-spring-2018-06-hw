package ru.otus.spring.kushchenko.hw7.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.otus.spring.kushchenko.hw7.model.Book
import ru.otus.spring.kushchenko.hw7.model.ShortBook

/**
 * Created by Елена on Июль, 2018
 */
interface BookRepository: JpaRepository<Book, Int> {
    fun findBy(): List<ShortBook>
}