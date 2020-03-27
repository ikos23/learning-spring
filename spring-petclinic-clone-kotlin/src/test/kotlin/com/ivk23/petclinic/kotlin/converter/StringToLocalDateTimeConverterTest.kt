package com.ivk23.petclinic.kotlin.converter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.Month

class StringToLocalDateTimeConverterTest {

    private val classUnderTest = StringToLocalDateTimeConverter()

    @Test
    fun `convert when empty input`() {
        assertNull(classUnderTest.convert(""))
    }

    @Test
    fun convert() {
        val expected = LocalDate.of(2020, Month.NOVEMBER, 23)
        assertEquals(expected, classUnderTest.convert("2020-11-23"))
    }
}