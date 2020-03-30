package com.ivk23.petclinic.kotlin.controller

import com.ivk23.petclinic.kotlin.model.Vet
import com.ivk23.petclinic.kotlin.service.PersonService
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@ExtendWith(MockitoExtension::class)
class VetControllerTest {

    @Mock
    private lateinit var personService: PersonService

    private lateinit var classUnderTest: VetController

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        classUnderTest = VetController(personService)
        mockMvc = MockMvcBuilders.standaloneSetup(classUnderTest).build()
    }

    @Test
    fun vets() {
        `when`(personService.getAllVets()).thenReturn(setOf(Vet()))

        mockMvc.get("/vets").andExpect {
            status { isOk }
            view {
                name("person/vets")
            }
            model {
                attribute("vets", hasSize<Set<Vet>>(1))
            }
        }

        verify(personService).getAllVets()
    }
}