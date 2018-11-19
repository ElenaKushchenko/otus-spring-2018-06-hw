package ru.otus.spring.kushchenko.hw18.repository

import org.springframework.data.mongodb.repository.MongoRepository
import ru.otus.spring.kushchenko.hw18.model.Comment

interface CommentRepository : MongoRepository<Comment, String>