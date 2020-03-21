package com.ivk23.petclinic.kotlin.controller

import com.ivk23.petclinic.kotlin.model.Owner
import com.ivk23.petclinic.kotlin.model.Pet
import com.ivk23.petclinic.kotlin.service.PersonService
import com.ivk23.petclinic.kotlin.service.PetService
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDate.of
import java.time.Month.NOVEMBER

@ExtendWith(MockitoExtension::class)
class OwnerControllerTest {

    @Mock
    private lateinit var personService: PersonService

    @Mock
    private lateinit var petService: PetService

    private lateinit var classUnderTest: OwnerController

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        classUnderTest = OwnerController(personService, petService)
        mockMvc = MockMvcBuilders.standaloneSetup(classUnderTest).build()
    }

    // why backticks ? Here is the link =)
    // https://kotlinlang.org/docs/reference/java-interop.html#escaping-for-java-identifiers-that-are-keywords-in-kotlin
    @Test
    fun owners() {
        `when`(personService.getAllOwners()).thenReturn(getTestOwners())

        mockMvc.get("/owners").andExpect {
            status { isOk }
            view { name("person/owners") }
            model {
                attribute("owners", hasSize<List<Owner>>(2))
                hasNoErrors()
            }
        }

        verify(personService).getAllOwners()
    }

    @Test
    fun ownerProfile() {
        `when`(personService.getOwnerById(1L))
                .thenReturn(getTestOwners().find { it.id == 1L })

        mockMvc.get("/owners/1/profile")
                .andExpect {
                    status { isOk }
                    view { name("person/owner") }
                    model {
                        attribute("owner", hasProperty<Owner>("firstName"))
                        attribute("owner", hasProperty<Owner>("lastName", equalTo("Duck")))
                        attribute("owner", hasProperty<Owner>("city", equalTo("DuckCity")))
                        attribute("owner", hasProperty<Owner>("pets", hasSize<List<Owner>>(0)))
                    }
                }

        verify(personService).getOwnerById(1L)
    }

    @Test
    fun `create owner get`() {
        mockMvc.get("/owners/create")
                .andExpect {
                    status { isOk }
                    view { name("person/createOwner") }
                }
    }

    @Test
    fun `create owner post`() {
        val owner = Owner()
        owner.pets = mutableSetOf()
        owner.firstName = "Foo"
        owner.lastName = "Bar"
        owner.address = "FooBar"
        owner.city = "FooCity"
        owner.telephone = "123456"

        `when`(personService.create(owner)).thenReturn(owner)

        mockMvc.post("/owners/create") {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
            content = "firstName=Foo&lastName=Bar&address=FooBar&city=FooCity&telephone=123456"
        }.andExpect {
            status { is3xxRedirection }
        }

        verify(personService).create(owner)
    }

    private fun getTestOwners(): List<Owner> {
        val first = Owner()
        first.id = 1L
        first.firstName = "Donald"
        first.lastName = "Duck"
        first.telephone = "123-555-321"
        first.city = "DuckCity"
        first.address = "No ave., 123"
        first.pets = mutableSetOf()

        val second = Owner()
        second.id = 2L
        second.firstName = "Tony"
        second.lastName = "Stark"
        second.telephone = "222-555-888"
        second.city = "New York"
        second.address = "Stark Tower, 1"

        val pet = Pet()
        pet.id = 1L
        pet.type = "Dog"
        pet.name = "Lola"
        pet.birthDate = of(2019, NOVEMBER, 5)
        pet.ownerId = second.id
        second.pets = mutableSetOf(pet)

        return listOf(first, second)
    }

}