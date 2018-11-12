package ru.otus.spring.kushchenko.adminclient.service

/**
 * Created by Елена on Июль, 2018
 */
interface LibraryService {
    fun takeBook(bookId: String, userId: String)
    fun returnBook(bookId: String, userId: String)
}