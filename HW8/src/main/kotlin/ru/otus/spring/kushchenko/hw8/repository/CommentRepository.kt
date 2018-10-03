package ru.otus.spring.kushchenko.hw8.repository

import org.springframework.data.mongodb.repository.MongoRepository
import ru.otus.spring.kushchenko.hw8.model.Comment

interface CommentRepository : MongoRepository<Comment, String>