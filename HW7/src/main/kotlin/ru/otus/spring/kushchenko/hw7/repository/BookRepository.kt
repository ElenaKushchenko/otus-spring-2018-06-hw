package ru.otus.spring.kushchenko.hw7.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.otus.spring.kushchenko.hw7.model.Book
import ru.otus.spring.kushchenko.hw7.model.ShortBook

/**
 * Created by Елена on Июль, 2018
 */
interface BookRepository : JpaRepository<Book, Int> {
    @Query("select b from Book b")
    fun findAllShortBooks(): List<ShortBook>

    @Query("select b from Book b join Author a join Genre where a.Id = :authorId")
    fun find(
        @Param("name") name: String?,
        @Param("authorId") authorId: Int?,
        @Param("genreId") genreId: Int?,
        pageable: Pageable
    ): Page<ShortBook>

    fun findByNameAndAuthors_IdAndGenres_Id(
        name: String?,
        authorId: Int?,
        genreId: Int?,
        pageable: Pageable
    ): Page<ShortBook>
}