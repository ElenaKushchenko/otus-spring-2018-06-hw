package ru.otus.spring.kushchenko.hw5.service

import org.springframework.stereotype.Service
import ru.otus.spring.kushchenko.hw5.repository.LibraryDao

/**
 * Created by Елена on Июль, 2018
 */
@Service
class LibraryServiceImpl(private val libraryDao: LibraryDao) : LibraryService {

    override fun takeBook(bookId: Int, readerId: Int) =
        libraryDao.takeBook(bookId, readerId)

    override fun returnBook(bookId: Int, readerId: Int) =
        libraryDao.returnBook(bookId, readerId)
}