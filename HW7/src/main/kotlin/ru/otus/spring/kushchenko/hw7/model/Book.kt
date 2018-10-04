package ru.otus.spring.kushchenko.hw7.model

import javax.persistence.CascadeType.REFRESH
import javax.persistence.CascadeType.REMOVE
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.OneToMany
import javax.persistence.Table

/**
 * Created by Елена on Июль, 2018
 */
@Entity
@Table(name = "Book", schema = "otus_spring")
data class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false, insertable = false, updatable = false)
    val id: Int? = null,

    @Column(name = "Name", nullable = false)
    var name: String,

    @Column(name = "OriginalName")
    var originalName: String? = null,

    @Column(name = "Paperback")
    var paperback: Int? = null,

    @ManyToMany(cascade = [REFRESH])
    @JoinTable(
        name = "BookAuthor",
        schema = "otus_spring",
        joinColumns = [JoinColumn(name = "BookId")],
        inverseJoinColumns = [JoinColumn(name = "AuthorId")]
    )
    var authors: List<Author>? = emptyList(),

    @ManyToMany(cascade = [REFRESH], fetch = FetchType.EAGER)
    @JoinTable(
        name = "BookGenre",
        schema = "otus_spring",
        joinColumns = [JoinColumn(name = "BookId")],
        inverseJoinColumns = [JoinColumn(name = "GenreId")]
    )
    var genres: List<Genre>? = emptyList(),

    @OneToMany(cascade = [REMOVE])
    @JoinColumn(name = "BookId", insertable = false)
    var comments: List<Comment>? = emptyList()
)