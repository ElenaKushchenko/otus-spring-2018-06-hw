package ru.otus.spring.kushchenko.hw7.service

/**
 * Created by Елена on Июль, 2018
 */
interface LibraryService {
    fun takeBook(bookId: Int, userId: Int)
    fun returnBook(bookId: Int, userId: Int)
}