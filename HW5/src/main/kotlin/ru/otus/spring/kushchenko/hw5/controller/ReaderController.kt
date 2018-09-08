package ru.otus.spring.kushchenko.hw5.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.spring.kushchenko.hw5.model.Reader
import ru.otus.spring.kushchenko.hw5.service.ReaderService

/**
 * Created by Елена on Июль, 2018
 */
@RestController
@RequestMapping("/readers")
class ReaderController(private val service: ReaderService) {

    @GetMapping
    fun getAll(): List<Reader> =
        service.getAll()

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: Int): Reader? =
        service.get(id)

    @PostMapping
    fun create(@RequestBody reader: Reader): Reader =
        service.create(reader)

    @PutMapping("/{id}")
    fun update(
        @PathVariable("id") id: Int,
        @RequestBody reader: Reader
    ): Reader =
        service.update(id, reader)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Int) =
        service.delete(id)

}