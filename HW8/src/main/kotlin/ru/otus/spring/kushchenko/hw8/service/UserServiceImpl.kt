package ru.otus.spring.kushchenko.hw8.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.otus.spring.kushchenko.hw8.entity.User
import java.lang.IllegalArgumentException

/**
 * Created by Елена on Июль, 2018
 */
//@Service
//@Transactional
//class UserServiceImpl(private val userRepository: UserRepository) : UserService {
//    override fun getAll(): List<User> = userRepository.findAll()
//
//    override fun get(id: Int): User = userRepository.findById(id)
//        .orElseThrow { IllegalArgumentException("User with id = $id not found") }
//
//    override fun create(user: User) = userRepository.save(user)
//
//    override fun update(user: User): User {
//        val id = user.id!!
//
//        if (userRepository.existsById(id).not())
//            throw IllegalArgumentException("User with id = $id not found")
//
//        return userRepository.save(user)
//    }
//
//    override fun delete(id: Int) = userRepository.deleteById(id)
//}