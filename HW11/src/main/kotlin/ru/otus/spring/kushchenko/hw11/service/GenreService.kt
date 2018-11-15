package ru.otus.spring.kushchenko.hw11.service

import reactor.core.publisher.Flux

/**
 * Created by Елена on Июль, 2018
 */
interface GenreService {
    fun getAll(): Flux<String>
}