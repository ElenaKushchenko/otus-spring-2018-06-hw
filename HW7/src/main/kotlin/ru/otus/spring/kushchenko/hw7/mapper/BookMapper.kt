package ru.otus.spring.kushchenko.hw7.mapper

import ru.otus.spring.kushchenko.hw7.model.Author
import ru.otus.spring.kushchenko.hw7.model.Book
import ru.otus.spring.kushchenko.hw7.model.Comment
import ru.otus.spring.kushchenko.hw7.model.Genre
import ru.otus.spring.kushchenko.hw7.model.User
import ru.otus.spring.kushchenko.hw7.model.dto.BookDto

object BookMapper {

    fun Book.toDto() =
        BookDto(
            id = this.id,
            name = this.name,
            originalName = this.originalName,
            paperback = this.paperback,
            authorIds = this.authors?.map { it.id!! },
            genreIds = this.genres?.map { it.id!! },
            userId = this.user?.id,
            commentIds = this.comments?.map { it.id!! }
        )

    fun BookDto.toEntity() =
        Book(
            id = this.id,
            name = this.name,
            originalName = this.originalName,
            paperback = this.paperback,
            authors = this.authorIds?.map { Author(id = it) },
            genres = this.genreIds?.map { Genre(id = it) },
            user = this.userId?.let { User(id = it) },
            comments = this.commentIds?.map { Comment(id = it) }
        )
}