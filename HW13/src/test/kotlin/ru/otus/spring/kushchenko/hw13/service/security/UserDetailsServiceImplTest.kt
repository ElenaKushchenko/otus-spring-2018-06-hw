package ru.otus.spring.kushchenko.hw13.service.security

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UsernameNotFoundException
import ru.otus.spring.kushchenko.hw13.model.UserRole.*
import ru.otus.spring.kushchenko.hw13.repository.UserRepository

import org.springframework.security.core.userdetails.User as SpringUser
import ru.otus.spring.kushchenko.hw13.model.User as MyUser

/**
 * Created by Елена on Нояб., 2018
 */
class UserDetailsServiceImplTest {
    private val userRepository: UserRepository = mock()
    private val service = UserDetailsServiceImpl(userRepository)

    @Nested
    @DisplayName("Tests for loadUserByUsername() method")
    inner class LoadUserByUsername {

        @Test
        fun shouldPassSuccessfully() {
            val username = "User"

            val myUser = MyUser(
                username = username,
                password = "Password",
                roles = listOf(USER, ADMIN)
            )
            val springUser = SpringUser(
                username,
                "Password",
                listOf(SimpleGrantedAuthority("USER"), SimpleGrantedAuthority("ADMIN"))
            )

            whenever(userRepository.findByUsername(username)).thenReturn(myUser)

            assertEquals(springUser, service.loadUserByUsername(username))

            verify(userRepository).findByUsername(username)
            verifyNoMoreInteractions(userRepository)
        }

        @Test
        fun shouldFailBecauseUserDoesNotExist() {
            val username = "User"

            whenever(userRepository.findByUsername(username)).thenReturn(null)

            assertThatThrownBy { service.loadUserByUsername(username) }
                .isInstanceOf(UsernameNotFoundException::class.java)

            verify(userRepository).findByUsername(username)
            verifyNoMoreInteractions(userRepository)
        }
    }
}