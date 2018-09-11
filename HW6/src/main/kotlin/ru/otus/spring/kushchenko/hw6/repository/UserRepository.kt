package ru.otus.spring.kushchenko.hw6.repository

import ru.otus.spring.kushchenko.hw6.entity.User

/**
 * Created by Елена on Июль, 2018
 */
interface UserRepository {
    fun getAll(): List<User>
    fun get(id: Int): User
    fun create(user: User): User
    fun update(id: Int, user: User): User
    fun delete(id: Int)
}