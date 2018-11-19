package ru.otus.spring.kushchenko.hw18.service

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.springframework.stereotype.Service
import ru.otus.spring.kushchenko.hw18.repository.GenreRepository

@Service
class GenreServiceImpl(private val repository: GenreRepository) : GenreService {
    @HystrixCommand(groupKey = "Genre", commandKey = "GetAll")
    override fun getAll(): List<String> = repository.getAll()
}