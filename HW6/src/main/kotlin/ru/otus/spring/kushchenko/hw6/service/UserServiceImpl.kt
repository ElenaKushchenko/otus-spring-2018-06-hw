package ru.otus.spring.kushchenko.hw6.service

import org.springframework.stereotype.Service
import ru.otus.spring.kushchenko.hw6.entity.User
import ru.otus.spring.kushchenko.hw6.repository.UserRepository

/**
 * Created by Елена on Июль, 2018
 */
@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserService {
    override fun getAll() = userRepository.getAll()

    override fun get(id: Int) = userRepository.get(id)

    override fun create(user: User) = userRepository.create(user)

    override fun update(id: Int, user: User) = userRepository.update(id, user)

    override fun delete(id: Int) = userRepository.delete(id)
}