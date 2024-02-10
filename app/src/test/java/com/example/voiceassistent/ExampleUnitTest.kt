package com.example.voiceassistent

import com.example.voiceassistent.holidays.ParsingHtmlService

import org.junit.Assert.*
import org.junit.jupiter.api.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun correctHoliday() {
        ParsingHtmlService().getHoliday("1 января 2024")
    }
}