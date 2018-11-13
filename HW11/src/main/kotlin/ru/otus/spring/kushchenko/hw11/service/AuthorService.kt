package ru.otus.spring.kushchenko.hw11.service

import reactor.core.publisher.Flux
import ru.otus.spring.kushchenko.hw11.model.Author

/**
 * Created by Елена on Июль, 2018
 */
interface AuthorService {
    fun getAll(): Flux<Author>
}