package ru.otus.spring.kushchenko.hw6.entity

import ru.otus.spring.kushchenko.hw6.dto.AuthorRequest
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

/**
 * Created by Елена on Июль, 2018
 */
@Entity
@Table(name = "Author", schema = "otus_spring")
data class Author(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false, insertable = false, updatable = false)
    val id: Int? = null,

    @Column(name = "Name", nullable = false)
    var name: String? = null
) {
    constructor(dto: AuthorRequest): this(name = dto.name)
}