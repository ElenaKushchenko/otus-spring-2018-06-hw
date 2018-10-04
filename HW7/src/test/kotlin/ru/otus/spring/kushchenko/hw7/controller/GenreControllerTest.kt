//package ru.otus.spring.kushchenko.hw7.controller
//
//import com.nhaarman.mockito_kotlin.doNothing
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
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
//import ru.otus.spring.kushchenko.hw7.dto.GenreRequest
//import ru.otus.spring.kushchenko.hw7.entity.Genre
//import ru.otus.spring.kushchenko.hw7.service.GenreService
//import ru.otus.spring.kushchenko.hw7.util.Util.asJsonString
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
//    fun getShortBooks() {
//        val genres = listOf(
//            Genre(1, "Genre1"),
//            Genre(2, "Genre2")
//        )
//
//        whenever(service.getShortBooks()).thenReturn(genres)
//
//        mockMvc.perform(get(BASE_URL))
//            .andExpect(status().isOk)
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(content().json(genres.asJsonString()))
//            .andReturn().response
//
//        verify(service).getShortBooks()
//        verifyNoMoreInteractions(service)
//    }
//
//    @Test
//    fun get() {
//        val genreId = 1
//        val genre = Genre(genreId, "Genre1")
//
//        whenever(service.get(genreId)).thenReturn(genre)
//
//        mockMvc.perform(get("$BASE_URL/{id}", genreId))
//            .andExpect(status().isOk)
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(content().json(genre.asJsonString()))
//            .andReturn().response
//
//        verify(service).get(genreId)
//        verifyNoMoreInteractions(service)
//    }
//
//    @Test
//    fun create() {
//        val genre = Genre(1, "Genre1")
//        val genreRequest = genre.toRequest()
//
//        whenever(service.create(Genre(genreRequest))).thenReturn(genre)
//
//        mockMvc.perform(
//            post(BASE_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(genreRequest.asJsonString())
//        ).andDo(::print)
//            .andExpect(status().isOk)
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(content().json(genre.asJsonString()))
//            .andReturn().response
//
//        verify(service).create(Genre(genreRequest))
//        verifyNoMoreInteractions(service)
//    }
//
//    @Test
//    fun update() {
//        val genreId = 1
//        val genre = Genre(genreId, "Genre1")
//        val genreRequest = genre.toRequest()
//
//        whenever(service.update(Genre(genreRequest, genreId))).thenReturn(genre)
//
//        mockMvc.perform(
//            put("$BASE_URL/{id}", genreId)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(genreRequest.asJsonString())
//        ).andDo(::print)
//            .andExpect(status().isOk)
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(content().json(genre.asJsonString()))
//            .andReturn().response
//
//        verify(service).update(Genre(genreRequest, genreId))
//        verifyNoMoreInteractions(service)
//    }
//
//    @Test
//    fun delete() {
//        val genreId = 1
//
//        doNothing().whenever(service).delete(genreId)
//        mockMvc.perform(
//            delete("$BASE_URL/{id}", genreId)
//        )
//            .andExpect(status().isOk)
//
//        verify(service).delete(genreId)
//        verifyNoMoreInteractions(service)
//    }
//
//    private fun Genre.toRequest() =
//            GenreRequest(name = this.name!!)
//}