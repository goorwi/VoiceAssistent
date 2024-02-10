package com.example.voiceassistent.holidays

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ParsingHtmlServiceTest {

    @Test
    fun getHoliday() {
        val holiday = ParsingHtmlService().getHoliday("1 января 2024")
        assertEquals("Новый год", holiday)
    }
}