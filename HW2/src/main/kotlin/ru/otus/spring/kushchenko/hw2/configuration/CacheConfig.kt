package ru.otus.spring.kushchenko.hw2.configuration

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

/**
 * Created by Елена on Июль, 2018
 */
@Configuration
@EnableCaching
class CacheConfig {

    @Bean
    fun cacheManager() =
            CaffeineCacheManager("questions").apply {
                isAllowNullValues = false
                setCaffeine(caffeineCacheBuilder())
            }

    private fun caffeineCacheBuilder() =
            Caffeine.newBuilder()
                    .initialCapacity(10)
                    .maximumSize(150)
                    .expireAfterWrite(10, TimeUnit.MINUTES)
}