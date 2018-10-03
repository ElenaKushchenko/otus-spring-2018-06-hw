package ru.otus.spring.kushchenko.hw7.mapper

import ru.otus.spring.kushchenko.hw7.model.Comment
import ru.otus.spring.kushchenko.hw7.model.dto.CommentDto

object CommentMapper {

    fun Comment.toDto() =
        CommentDto(
            id = this.id,
            text = this.text,


        )

    fun CommentDto.toEntity() =
        Comment()
}