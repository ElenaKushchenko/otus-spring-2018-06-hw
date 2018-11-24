package ru.otus.spring.kushchenko.hw18.service

/**
 * Created by Елена on Июль, 2018
 */
interface LibraryService {
    fun takeBook(bookId: String, userId: String)
    fun returnBook(bookId: String, userId: String)
}