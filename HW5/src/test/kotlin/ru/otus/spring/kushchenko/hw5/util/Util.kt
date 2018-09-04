package ru.otus.spring.kushchenko.hw5.util

import com.fasterxml.jackson.databind.ObjectMapper

/**
 * Created by Елена on Авг., 2018
 */
object Util {
    fun Any?.asJsonString(): String {
        try {
            return ObjectMapper().writeValueAsString(this)
        } catch (ex: Exception) {
            throw RuntimeException(ex)
        }
    }
}