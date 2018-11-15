package ru.otus.spring.kushchenko.hw12.service.security

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import ru.otus.spring.kushchenko.hw12.repository.UserRepository
import org.springframework.security.core.userdetails.User as SpringUser
import ru.otus.spring.kushchenko.hw12.model.User as MyUser

/**
 * Created by Елена on Нояб., 2018
 */
@Service
class UserDetailsServiceImpl(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User $username not exists")
        return user.toUserDetails()
    }

    private fun MyUser.toUserDetails(): UserDetails {
        return SpringUser.builder()
            .username(this.username)
            .password(this.password)
            .roles()
            .build()
    }
}