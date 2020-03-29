package com.ivk23.petclinic.kotlin.model

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class VisitTest {

    @Test
    fun testEquals() {
        val v1 = Visit()
        v1.id = 1
        v1.description = "aaa"

        val v2 = Visit()
        v2.id = 1
        v2.description = "aaa"

        assertTrue { v1.equals(v2) }
    }

    @Test
    fun testHashCode() {
        val v1 = Visit()
        v1.id = 1
        v1.description = "aaa"

        val v2 = Visit()
        v2.id = 1
        v2.description = "aaa"

        assertEquals(v1.hashCode(), v2.hashCode())
    }
}