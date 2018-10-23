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

    @Query(
        """
        SELECT b
        FROM Book b
          JOIN Author a
          JOIN Genre g 
        WHERE (:name IS NULL OR b.name = :name)
          AND (:authorId IS NULL OR a.id = :authorId)
          AND (:genreId IS NULL OR g.id = :genreId)
    """
    )
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