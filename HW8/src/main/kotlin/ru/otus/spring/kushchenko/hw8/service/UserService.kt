package ru.otus.spring.kushchenko.hw8.service

import ru.otus.spring.kushchenko.hw8.entity.User

/**
 * Created by Елена on Июль, 2018
 */
interface UserService {
    fun getAll(): List<User>
    fun get(id: String): User
    fun create(user: User): User
    fun update(user: User): User
    fun delete(id: String)
}