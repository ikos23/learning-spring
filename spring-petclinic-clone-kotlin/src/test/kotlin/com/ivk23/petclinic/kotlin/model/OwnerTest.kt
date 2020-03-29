package com.ivk23.petclinic.kotlin.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class OwnerTest {

    @Test
    fun `simple equals`() {
        assertEquals(Owner(), Owner())
    }

    @Test
    fun equals() {
        val o1 = Owner()
        o1.firstName = "xxxx"
        o1.lastName = "yyyy"
        o1.id = 1
        o1.telephone = "12345"
        o1.address = "aaaaa"
        o1.city = "cccc"

        val o2 = Owner()
        o2.firstName = "xxxx"
        o2.lastName = "yyyy"
        o2.id = 1
        o2.telephone = "12345"
        o2.address = "aaaaa"
        o2.city = "cccc"

        assertEquals(o1, o2)
    }

    @Test
    fun `using in map`() {
        val o1 = Owner()
        o1.firstName = "xxxx"
        o1.lastName = "yyyy"
        o1.id = 1

        val o2 = Owner()
        o2.firstName = "xxxx"
        o2.lastName = "yyyy"
        o2.id = 1

        val map = mapOf(Pair(o1, "OWNER 1"))

        assertEquals("OWNER 1", map[o2])
    }

}