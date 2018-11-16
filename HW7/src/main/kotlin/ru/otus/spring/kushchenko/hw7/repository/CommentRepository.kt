package ru.otus.spring.kushchenko.hw7.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.otus.spring.kushchenko.hw7.model.Comment

/**
 * Created by Елена on Июль, 2018
 */
interface CommentRepository: JpaRepository<Comment, Int>