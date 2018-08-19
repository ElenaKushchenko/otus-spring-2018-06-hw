package ru.otus.spring.kushchenko.hw5.repository

import ru.otus.spring.kushchenko.hw5.model.Reader

/**
 * Created by Елена on Июль, 2018
 */
interface ReaderRepository {
    fun getAll(): List<Reader>
    fun get(id: Int): Reader?
    fun create(reader: Reader): Reader
    fun update(id: Int, reader: Reader): Reader
    fun delete(id: Int)
}