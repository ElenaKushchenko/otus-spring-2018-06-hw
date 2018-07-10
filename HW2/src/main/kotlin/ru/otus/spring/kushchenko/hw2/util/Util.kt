package ru.otus.spring.kushchenko.hw2.util

/**
 * Created by Елена on Июль, 2018
 */
object Util {
    fun String?.notBlankOrDefault(default: String) =
            if (this.isNullOrBlank()) default else this
}