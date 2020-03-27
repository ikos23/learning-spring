package com.ivk23.petclinic.kotlin.controller

import com.ivk23.petclinic.kotlin.converter.StringToLocalDateTimeConverter
import com.ivk23.petclinic.kotlin.model.Owner
import com.ivk23.petclinic.kotlin.model.Visit
import com.ivk23.petclinic.kotlin.service.PersonService
import com.ivk23.petclinic.kotlin.service.VisitService
import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.format.support.FormattingConversionService
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
class VisitControllerTest {

    @Mock
    private lateinit var personService: PersonService

    @Mock
    private lateinit var visitService: VisitService

    private lateinit var classUnderTest: VisitController

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        classUnderTest = VisitController(visitService, personService)

        val formattingConversionService = FormattingConversionService()
        formattingConversionService.addConverter(StringToLocalDateTimeConverter())
        mockMvc = MockMvcBuilders.standaloneSetup(classUnderTest)
                .setConversionService(formattingConversionService)
                .build()
    }

    @Test
    fun index() {
        `when`(visitService.getAll()).thenReturn(listOf(Visit(), Visit()))

        mockMvc.get("/visits")
                .andExpect {
                    status { isOk }
                    view { name("visit/index") }
                    model {
                        attribute("visits", Matchers.hasSize<List<Visit>>(2))
                    }
                }

        verify(visitService).getAll()
    }

    @Test
    fun `get create`() {
        `when`(personService.getAllOwners()).thenReturn(listOf(Owner(), Owner(), Owner()))

        mockMvc.get("/visits/create")
                .andExpect {
                    status { isOk }
                    view { name("visit/create") }
                    model {
                        attribute("owners", Matchers.hasSize<List<Owner>>(3))
                    }
                }

        verify(personService).getAllOwners()

    }

    @Test
    fun `post create`() {
        val visit = Visit()
        visit.petId = 123
        visit.visitDate = LocalDate.of(2020, 12, 12)
        visit.description = "descr"

        `when`(visitService.create(visit)).thenReturn(visit)

        mockMvc.post("/visits") {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
            content = "petId=123&visitDate=2020-12-12&description=descr"
        }.andExpect {
            status { is3xxRedirection }
        }

        verify(visitService).create(visit)
    }
}