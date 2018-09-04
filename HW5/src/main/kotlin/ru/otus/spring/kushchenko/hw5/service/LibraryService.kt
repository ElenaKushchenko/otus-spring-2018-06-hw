package ru.otus.spring.kushchenko.hw5.service

/**
 * Created by Елена on Июль, 2018
 */
interface LibraryService {
    fun takeBook(bookId: Int, readerId: Int)
    fun returnBook(bookId: Int, readerId: Int)
}