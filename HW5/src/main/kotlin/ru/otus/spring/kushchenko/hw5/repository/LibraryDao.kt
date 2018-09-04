package ru.otus.spring.kushchenko.hw5.repository

/**
 * Created by Елена on Июль, 2018
 */
interface LibraryDao {
    fun takeBook(bookId: Int, readerId: Int)
    fun returnBook(bookId: Int, readerId: Int)
}