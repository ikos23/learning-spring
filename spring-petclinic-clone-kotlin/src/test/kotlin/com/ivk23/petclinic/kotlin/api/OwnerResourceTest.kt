package com.ivk23.petclinic.kotlin.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.ivk23.petclinic.kotlin.SpringBootTestBase
import com.ivk23.petclinic.kotlin.model.Owner
import com.ivk23.petclinic.kotlin.model.Pet
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDate
import java.time.Month.JANUARY
import kotlin.test.assertEquals
import kotlin.test.assertNotSame
import kotlin.test.assertTrue

/**
 * < Be careful, MockMvc DSL is here!  >
 * -----------------------
 *         \   ^__^
 *          \  (oo)\_______
 *             (__)\       )\/\
 *                 ||----w |
 *                 ||     ||
 * https://docs.spring.io/spring/docs/current/spring-framework-reference/languages.html#mockmvc-dsl
 */
class OwnerResourceTest : SpringBootTestBase() {

    lateinit var testOwner: Owner

    @BeforeEach
    fun before() {
        testOwner = Owner()
        testOwner.firstName = "Donald"
        testOwner.lastName = "Duck"
        testOwner.address = "Duck ave. 123"
        testOwner.city = "TheCity"
        testOwner.telephone = "123-555-123"

        testOwner = ownerRepository.saveAndFlush(testOwner)

        println("@BeforeEach : test owner created $testOwner")
    }

    @AfterEach
    fun after() {
        petRepository.deleteAll()
        ownerRepository.deleteAll()
        println("@AfterEach : clean up done")
    }

    @Test
    fun `get all owners`() {
        mockMvc.get("/api/owners") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk }
            jsonPath("$.length()") { value(1) }
            jsonPath("$[0].firstName") { value("Donald") }
            jsonPath("$[0].lastName") { value("Duck") }
            jsonPath("$[0].address") { value("Duck ave. 123") }
            jsonPath("$[0].city") { value("TheCity") }
            jsonPath("$[0].telephone") { value("123-555-123") }
        }.andDo {
            print()
        }
    }

    @Test
    fun `get owner by id`() {
        mockMvc.perform(get("/api/owners/${testOwner.id}"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.firstName").value("Donald"))
                .andExpect(jsonPath("$.lastName").value("Duck"))
                .andExpect(jsonPath("$.address").value("Duck ave. 123"))
                .andExpect(jsonPath("$.city").value("TheCity"))
                .andExpect(jsonPath("$.telephone").value("123-555-123"))
    }

    @Test
    fun `get by id owner not found`() {
        mockMvc.perform(get("/api/owners/99999"))
                .andExpect(status().isNotFound)
                .andExpect(content().string("Owner (id=99999) not found."))
    }

    @Test
    fun `create new owner`() {
        val o2 = Owner()
        o2.firstName = "Bart"
        o2.lastName = "Simpson"
        o2.address = "Test str. 1"
        o2.city = "SimCity"
        o2.telephone = "111-555-999"

        val json = objectMapper.writeValueAsString(o2)
        println("create owner test payload: $json")

        mockMvc.perform(post("/api/owners")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated)

        assertEquals(2, ownerRepository.findAll().size)
    }

    @Test
    fun `create new owner validation fail`() {
        val o3 = Owner()
        o3.firstName = "Bart"

        val json = ObjectMapper().writeValueAsString(o3)

        mockMvc.perform(post("/api/owners")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.telephone").value("must not be blank"))
                .andExpect(jsonPath("$.lastName").value("must not be blank"))

    }

    @Test
    fun `update existing owner`() {
        testOwner.firstName = "Foo"
        testOwner.lastName = "Bar"

        val json = ObjectMapper().writeValueAsString(testOwner)
        println("update owner test payload: $json")

        mockMvc.perform(put("/api/owners")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk)

        val fromDB = ownerRepository.findById(testOwner.id!!)
        println("fetched from db: $fromDB")

        assertNotSame(testOwner, fromDB.get())
        assertEquals("Foo", fromDB.get().firstName)
        assertEquals("Bar", fromDB.get().lastName)
        assertEquals("Duck ave. 123", fromDB.get().address)
        assertEquals("TheCity", fromDB.get().city)
        assertEquals("123-555-123", fromDB.get().telephone)

    }

    @Test
    fun `delete owner`() {
        mockMvc.perform(delete("/api/owners/${testOwner.id}"))
                .andExpect(status().isOk)
        assertTrue { ownerRepository.findById(testOwner.id!!).isEmpty }
    }

    @Test
    fun `delete owner not found`() {
        mockMvc.perform(delete("/api/owners/77777"))
                .andExpect(status().isNotFound)
                .andExpect(content().string("Owner (id=77777) not found."))
    }

    @Test
    fun `get owner pets`() {
        mockMvc.perform(get("/api/owners/${testOwner.id}/pets"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$").isEmpty)

        val lolaPet = Pet()
        lolaPet.name = "Lola 1"
        lolaPet.type = "Crocodile"
        lolaPet.ownerId = testOwner.id
        lolaPet.birthDate = LocalDate.of(2020, JANUARY, 14)
        petRepository.saveAndFlush(lolaPet)

        // let's check now :)
        mockMvc.perform(get("/api/owners/${testOwner.id}/pets"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Lola 1"))
                .andExpect(jsonPath("$[0].type").value("Crocodile"))
                .andExpect(jsonPath("$[0].birthDate").value("2020-01-14"))
    }

}