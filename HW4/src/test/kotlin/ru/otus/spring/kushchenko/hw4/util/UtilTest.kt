package ru.otus.spring.kushchenko.hw4.util

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import ru.otus.spring.kushchenko.hw4.util.Util.notBlankOrDefault
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName

/**
 * Created by Елена on Июль, 2018
 */
class UtilTest {

    @Nested
    @DisplayName("Tests for the method notBlankOrDefault()")
    inner class NotBlankOrDefault {

        @Test
        fun shouldReturnCurrentValue() {
            val currentValue = "current"
            val defaultValue = "default"
            assertThat(currentValue.notBlankOrDefault(defaultValue)).isEqualTo(currentValue)
        }

        @Test
        fun shouldReturnDefaultValueWhenCurrentIsNull() {
            val currentValue = null
            val defaultValue = "default"
            assertThat(currentValue.notBlankOrDefault(defaultValue)).isEqualTo(defaultValue)
        }

        @Test
        fun shouldReturnDefaultValueWhenCurrentIsBlank() {
            val currentValue = " "
            val defaultValue = "default"
            assertThat(currentValue.notBlankOrDefault(defaultValue)).isEqualTo(defaultValue)
        }
    }

}