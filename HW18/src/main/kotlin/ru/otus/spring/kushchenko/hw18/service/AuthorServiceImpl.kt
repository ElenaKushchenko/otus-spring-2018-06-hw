package ru.otus.spring.kushchenko.hw18.service

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.springframework.stereotype.Service
import ru.otus.spring.kushchenko.hw18.model.Author
import ru.otus.spring.kushchenko.hw18.repository.AuthorRepository

/**
 * Created by Елена on Июль, 2018
 */
@Service
class AuthorServiceImpl(private val repository: AuthorRepository) : AuthorService {
    @HystrixCommand(groupKey = "Author", commandKey = "GetAll")
    override fun getAll(): List<Author> = repository.getAll()
}