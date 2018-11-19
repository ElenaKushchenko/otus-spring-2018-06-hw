package ru.otus.spring.kushchenko.hw13.service

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit.jupiter.SpringExtension
import ru.otus.spring.kushchenko.hw13.repository.GenreRepository

@ExtendWith(SpringExtension::class)
@SpringBootTest
class GenreServiceImplTest {
    @MockBean
    private lateinit var repository: GenreRepository
    @Autowired
    private lateinit var service: GenreService

    @Test
    @WithMockUser
    fun getAll() {
        val genres = listOf("Genre1", "Genre2")

        whenever(repository.getAll()).thenReturn(genres)

        assertEquals(genres, service.getAll())

        verify(repository).getAll()
        verifyNoMoreInteractions(repository)
    }
}