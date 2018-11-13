//package ru.otus.spring.kushchenko.hw11.controller
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
//import org.springframework.test.context.junit.jupiter.SpringExtension
//import org.springframework.test.web.servlet.MockMvc
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
//
///**
// * Created by Елена on Авг., 2018
// */
//@ExtendWith(SpringExtension::class)
//@WebMvcTest(LibraryController::class)
//internal class LibraryControllerTest {
//    private val BASE_URL = "/library"
//
//    @Autowired
//    private lateinit var mockMvc: MockMvc
//    @MockBean
//    private lateinit var service: LibraryService
//
//    @Test
//    fun takeBook() {
//        val bookId = "1"
//        val readerId = "2"
//
//        doNothing().whenever(service).takeBook(bookId, readerId)
//
//        mockMvc.perform(
//            post("$BASE_URL/take")
//                .param("bookId", bookId)
//                .param("userId", readerId)
//        )
//            .andExpect(status().isOk)
//
//        verify(service).takeBook(bookId, readerId)
//        verifyNoMoreInteractions(service)
//    }
//
//    @Test
//    fun returnBook() {
//        val bookId = "1"
//        val readerId = "2"
//
//        doNothing().whenever(service).returnBook(bookId, readerId)
//
//        mockMvc.perform(
//            delete("$BASE_URL/return")
//                .param("bookId", bookId)
//                .param("userId", readerId)
//        )
//            .andExpect(status().isOk)
//
//        verify(service).returnBook(bookId, readerId)
//        verifyNoMoreInteractions(service)
//    }
//}