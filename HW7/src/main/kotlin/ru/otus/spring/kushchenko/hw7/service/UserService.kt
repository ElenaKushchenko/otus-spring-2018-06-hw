package ru.otus.spring.kushchenko.hw7.service

import ru.otus.spring.kushchenko.hw7.entity.User

/**
 * Created by Елена on Июль, 2018
 */
interface UserService {
    fun getAll(): List<User>
    fun get(id: Int): User
    fun create(user: User): User
    fun update(user: User): User
    fun delete(id: Int)
}