package ru.otus.spring.kushchenko.hw14.model.jpa

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
import javax.persistence.CascadeType.REFRESH
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

/**
 * Created by Елена on Авг., 2018
 */
@Entity
@Table(name = "Comment", schema = "otus_spring")
data class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false, insertable = false, updatable = false)
    val id: Int? = null,

    @Column(name = "Text", nullable = false)
    val text: String,

    @Column(name = "Date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val date: LocalDateTime? = LocalDateTime.now(),

    @ManyToOne(cascade = [REFRESH], optional = false)
    @JoinColumn(name = "UserId", nullable = false)
    val user: User,

    @Column(name = "BookId", nullable = false)
    val bookId: Int
)