package ru.otus.spring.kushchenko.hw14.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.otus.spring.kushchenko.hw14.model.jpa.Book

/**
 * Created by Елена on Июль, 2018
 */
interface JpaBookRepository : JpaRepository<Book, Int>