package ru.otus.spring.kushchenko.hw5.service

import org.springframework.stereotype.Service
import ru.otus.spring.kushchenko.hw5.model.Reader
import ru.otus.spring.kushchenko.hw5.repository.ReaderRepository

/**
 * Created by Елена on Июль, 2018
 */
@Service
class ReaderServiceImpl(private val readerRepository: ReaderRepository) : ReaderService {
    override fun getAll() = readerRepository.getAll()

    override fun get(id: Int) = readerRepository.get(id)

    override fun create(reader: Reader) = readerRepository.create(reader)

    override fun update(id: Int, reader: Reader) = readerRepository.update(id, reader)

    override fun delete(id: Int) = readerRepository.delete(id)
}