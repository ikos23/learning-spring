package com.ivk23.petclinic.kotlin.controller

import com.ivk23.petclinic.kotlin.converter.StringToLocalDateTimeConverter
import com.ivk23.petclinic.kotlin.model.Owner
import com.ivk23.petclinic.kotlin.model.Vet
import com.ivk23.petclinic.kotlin.service.PersonService
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.format.support.FormattingConversionService
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@ExtendWith(MockitoExtension::class)
class PersonControllerTest {

    @Mock
    private lateinit var personService: PersonService

    private lateinit var classUnderTest: PersonController

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        classUnderTest = PersonController(personService)

        val formattingConversionService = FormattingConversionService()
        formattingConversionService.addConverter(StringToLocalDateTimeConverter())
        mockMvc = MockMvcBuilders.standaloneSetup(classUnderTest)
                .setConversionService(formattingConversionService)
                .build()
    }

    @Test
    fun persons() {
        `when`(personService.getAllOwners()).thenReturn(listOf(Owner(), Owner()))
        `when`(personService.getAllVets()).thenReturn(setOf(Vet()))

        mockMvc.get("/persons")
                .andExpect {
                    status { isOk }
                    view {
                        name("person/index")
                    }
                    model {
                        attribute("owners", hasSize<List<Owner>>(2))
                        attribute("vets", hasSize<Set<Vet>>(1))
                    }
                }

        verify(personService).getAllOwners()
        verify(personService).getAllVets()
    }


    @Test
    fun vetProfile() {
        val vet = Vet()
        vet.id = 1
        vet.firstName = "Foo"

        `when`(personService.getVetById(1L)).thenReturn(vet)

        mockMvc.get("/vets/1/profile")
                .andExpect {
                    status { isOk }
                    view {
                        name("person/vet")
                    }
                    model {
                        attribute("vet", hasProperty<Vet>("firstName", equalTo("Foo")))
                    }
                }

        verify(personService).getVetById(1L)
    }
}