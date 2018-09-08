package ru.otus.spring.kushchenko.hw7.entity

import ru.otus.spring.kushchenko.hw7.dto.UserRequest
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
@Table(name = "User", schema = "otus_spring")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false, insertable = false, updatable = false)
    val id: Int? = null,

    @Column(name = "Name", nullable = false)
    var name: String? = null
) {
    constructor(dto: UserRequest, id: Int? = null): this(id = id, name = dto.name)
}