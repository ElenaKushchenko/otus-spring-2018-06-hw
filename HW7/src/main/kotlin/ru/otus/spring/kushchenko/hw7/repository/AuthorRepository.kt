package ru.otus.spring.kushchenko.hw7.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.otus.spring.kushchenko.hw7.entity.Author

/**
 * Created by Елена on Июль, 2018
 */
interface AuthorRepository: JpaRepository<Author, Int>