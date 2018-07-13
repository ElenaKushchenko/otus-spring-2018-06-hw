package ru.otus.spring.kushchenko.hw4.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import java.util.*

/**
 * Created by Елена on Июль, 2018
 */
@Configuration
class LocalizationConfig {

    @Bean
    fun locale(@Value("\${locale}") locale: String) =
            Locale(locale)

    @Bean
    fun messageSource() =
            ReloadableResourceBundleMessageSource().apply {
                setBasename("/i18n/lang")
                setDefaultEncoding("UTF-8")
                setCacheSeconds(1000)
            }
}