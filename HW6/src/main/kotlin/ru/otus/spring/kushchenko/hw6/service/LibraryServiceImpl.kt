package ru.otus.spring.kushchenko.hw6.service

import org.springframework.stereotype.Service
import ru.otus.spring.kushchenko.hw6.repository.LibraryDao

/**
 * Created by Елена on Июль, 2018
 */
@Service
class LibraryServiceImpl(private val libraryDao: LibraryDao) : LibraryService {

    override fun takeBook(bookId: Int, userId: Int) = libraryDao.takeBook(bookId, userId)

    override fun returnBook(bookId: Int, userId: Int) = libraryDao.returnBook(bookId, userId)
}