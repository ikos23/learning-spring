package com.ivk23.petclinic.kotlin.controller

import com.ivk23.petclinic.kotlin.service.PersonService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@ExtendWith(MockitoExtension::class)
class HomeControllerTest {

    @Mock
    private lateinit var personService: PersonService

    private lateinit var classUnderTest: HomeController

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        classUnderTest = HomeController(personService)

        mockMvc = MockMvcBuilders
                .standaloneSetup(classUnderTest)
                .build()
    }

    @AfterEach
    fun tearDown() {
    }

    // MockMvc DSL. Kotlin is cool :D
    @Test
    fun index() {
        // why backticks ? Here is the link =)
        // https://kotlinlang.org/docs/reference/java-interop.html#escaping-for-java-identifiers-that-are-keywords-in-kotlin
        `when`(personService.getAllPersonsCount()).thenReturn(3)

        mockMvc.get("/")
                .andExpect {
                    status { isOk }
                    view {
                        name("index")
                    }
                    model {
                        attribute("personsTotal", 3L)
                    }
                }

        verify(personService).getAllPersonsCount()
    }
}