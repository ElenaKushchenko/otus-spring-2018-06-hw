package ru.otus.spring.kushchenko.hw9.controller

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.ModelAndView

/**
 * Created by Елена on Сент., 2018
 */
@ControllerAdvice(annotations = [Controller::class])
class ControllerAdvice {

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    private fun generalError(ex: Exception) =
        ModelAndView().apply {
            addObject("message", if (ex.message.isNullOrBlank()) "Unknown Error" else ex.message)
            addObject("type", ex.javaClass.simpleName)
            viewName = "error"
        }
}