package ru.otus.spring.kushchenko.hw13.service.security

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import ru.otus.spring.kushchenko.hw13.repository.UserRepository
import org.springframework.security.core.userdetails.User as SpringUser
import ru.otus.spring.kushchenko.hw13.model.User as MyUser

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

    private fun MyUser.toUserDetails() =
        SpringUser(
            this.username,
            this.password,
            this.roles.map { SimpleGrantedAuthority(it.name) }
        )
}