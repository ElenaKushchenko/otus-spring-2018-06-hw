package ru.otus.spring.kushchenko.adminclient.service

import ru.otus.spring.kushchenko.adminclient.model.Author

/**
 * Created by Елена on Июль, 2018
 */
interface AuthorService {
    fun getAll(): List<Author>
}