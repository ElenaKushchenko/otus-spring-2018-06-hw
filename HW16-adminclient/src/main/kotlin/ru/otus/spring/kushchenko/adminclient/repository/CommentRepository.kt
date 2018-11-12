package ru.otus.spring.kushchenko.adminclient.repository

import org.springframework.data.mongodb.repository.MongoRepository
import ru.otus.spring.kushchenko.adminclient.model.Comment

interface CommentRepository : MongoRepository<Comment, String>