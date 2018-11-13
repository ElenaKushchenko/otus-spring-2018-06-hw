//package ru.otus.spring.kushchenko.hw11.controller
//
//import com.nhaarman.mockito_kotlin.verify
//import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
//import com.nhaarman.mockito_kotlin.whenever
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.extension.ExtendWith
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
//import org.springframework.boot.test.mock.mockito.MockBean
//import org.springframework.http.MediaType
//import org.springframework.test.context.junit.jupiter.SpringExtension
//import org.springframework.test.web.servlet.MockMvc
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
//import ru.otus.spring.kushchenko.hw11.service.GenreService
//import ru.otus.spring.kushchenko.hw11.util.Util.asJsonString
//
///**
// * Created by Елена on Авг., 2018
// */
//@ExtendWith(SpringExtension::class)
//@WebMvcTest(GenreController::class)
//class GenreControllerTest {
//    private val BASE_URL = "/genres"
//
//    @Autowired
//    private lateinit var mockMvc: MockMvc
//    @MockBean
//    private lateinit var service: GenreService
//
//    @Test
//    fun getAll() {
//        val genres = listOf("Genre1", "Genre2")
//
//        whenever(service.getAll()).thenReturn(genres)
//
//        mockMvc.perform(get(BASE_URL))
//            .andExpect(status().isOk)
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(content().json(genres.asJsonString()))
//            .andReturn().response
//
//        verify(service).getAll()
//        verifyNoMoreInteractions(service)
//    }
//}