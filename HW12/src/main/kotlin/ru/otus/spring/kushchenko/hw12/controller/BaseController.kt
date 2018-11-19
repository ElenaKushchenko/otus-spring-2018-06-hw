package ru.otus.spring.kushchenko.hw12.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

/**
 * Created by Елена on Нояб., 2018
 */
@Controller
@RequestMapping("/")
class BaseController {

    @GetMapping
    fun index(): String {
        return "index"
    }
}