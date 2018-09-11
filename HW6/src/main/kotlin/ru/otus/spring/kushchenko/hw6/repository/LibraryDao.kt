package ru.otus.spring.kushchenko.hw6.repository

/**
 * Created by Елена on Июль, 2018
 */
interface LibraryDao {
    fun takeBook(bookId: Int, userId: Int)
    fun returnBook(bookId: Int, userId: Int)
}