package com.ivk23.petclinic.kotlin.api

import com.ivk23.petclinic.kotlin.SpringBootTestBase
import com.ivk23.petclinic.kotlin.model.Owner
import com.ivk23.petclinic.kotlin.model.Pet
import com.ivk23.petclinic.kotlin.repository.OwnerRepository
import com.ivk23.petclinic.kotlin.repository.PetRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDate
import java.time.LocalDate.of
import java.time.Month.FEBRUARY
import java.time.Month.JANUARY
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class PetResourceTest : SpringBootTestBase() {

    lateinit var testOwner: Owner

    @BeforeEach
    fun setUp() {
        testOwner = Owner()
        testOwner.firstName = "John"
        testOwner.lastName = "Snow"
        testOwner.address = "Winterfell Room 4"
        testOwner.city = "Winterfell"
        testOwner.telephone = "123-555-123"

        testOwner = ownerRepository.saveAndFlush(testOwner)

        println("@BeforeEach : test owner created $testOwner")
    }

    @AfterEach
    fun tearDown() {
        petRepository.deleteAll()
        ownerRepository.deleteAll()
        println("@AfterEach : clean up done")
    }

    @Test
    fun `get pets`() {
        persistTestPet("Foo", "Dog")
        persistTestPet("Bar", "Hamster")

        mockMvc.perform(get("/api/pets"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Bar"))
                .andExpect(jsonPath("$[0].type").value("Hamster"))
                .andExpect(jsonPath("$[0].birthDate").value("2020-02-02"))
                .andExpect(jsonPath("$[0].ownerId").value("${testOwner.id}"))
                .andExpect(jsonPath("$[1].name").value("Foo"))
                .andExpect(jsonPath("$[1].type").value("Dog"))
                .andExpect(jsonPath("$[1].birthDate").value("2020-02-02"))
                .andExpect(jsonPath("$[1].ownerId").value("${testOwner.id}"))

    }

    @Test
    fun `get pets with page and size params`() {
        persistTestPet("Hugo", "Dog")
        persistTestPet("Barry", "Hamster")
        persistTestPet("Alfred", "Dog")
        persistTestPet("Cookie", "Cat", of(2019, 11, 16))

        mockMvc.perform(get("/api/pets?page=2&size=2"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Cookie"))
                .andExpect(jsonPath("$[0].type").value("Cat"))
                .andExpect(jsonPath("$[0].birthDate").value("2019-11-16"))
                .andExpect(jsonPath("$[0].ownerId").value("${testOwner.id}"))
                .andExpect(jsonPath("$[1].name").value("Hugo"))
                .andExpect(jsonPath("$[1].type").value("Dog"))
                .andExpect(jsonPath("$[1].birthDate").value("2020-02-02"))
                .andExpect(jsonPath("$[1].ownerId").value("${testOwner.id}"))
    }

    @Test
    fun `get pets with invalid page and size params`() {
        mockMvc.perform(get("/api/pets?page=0&size=5000"))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.['get.page']").value("must be greater than or equal to 1"))
                .andExpect(jsonPath("$.['get.size']").value("must be less than or equal to 100"))
    }

    @Test
    fun `get pet by id`() {
        val cookie = persistTestPet("Cookie", "Cat", of(2019, 11, 16))

        mockMvc.perform(get("/api/pets/${cookie.id}"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.name").value("Cookie"))
                .andExpect(jsonPath("$.type").value("Cat"))
                .andExpect(jsonPath("$.birthDate").value("2019-11-16"))
                .andExpect(jsonPath("$.ownerId").value("${testOwner.id}"))
    }

    @Test
    fun `get pet by id not found`() {
        mockMvc.perform(get("/api/pets/99999"))
                .andExpect(status().isNotFound)
                .andExpect(content().string("Pet (id=99999) not found."))
    }

    @Test
    fun `create pet`() {

        val petJson = getPetAsJson("Barry", "Dog", testOwner.id!!, of(2020, FEBRUARY, 2))

        mockMvc.perform(post("/api/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(petJson))
                .andExpect(status().isCreated)

        val pets = petRepository.findAll()
        assertEquals(1, pets.size)
        assertEquals("Barry", pets[0].name)
    }

    @Test
    fun `create pet owner not found`() {
        val petJson = getPetAsJson("Barry", "Dog", 12345, of(2020, FEBRUARY, 2))

        mockMvc.perform(post("/api/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(petJson))
                .andExpect(status().isNotFound)
                .andExpect(content().string("Owner (id=12345) not found."))

        // pet was not created
        assertTrue { petRepository.findAll().isEmpty() }
    }

    @Test
    fun `update pet`() {
        val millie = persistTestPet("Millie", "Dog")
        millie.birthDate = of(2018, JANUARY, 31)

        mockMvc.perform(put("/api/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(millie)))
                .andExpect(status().isOk)

        assertEquals(of(2018, JANUARY, 31),
                petRepository.findById(millie.id!!).get().birthDate)
    }

    @Test
    fun `update pet owner not found`() {
        val petJson = getPetAsJson("Barry", "Dog", 12345, of(2020, FEBRUARY, 2))

        mockMvc.perform(put("/api/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(petJson))
                .andExpect(status().isNotFound)
                .andExpect(content().string("Owner (id=12345) not found."))
    }

    @Test
    fun `delete pet`() {
        val millie = persistTestPet("Millie", "Dog")

        mockMvc.perform(delete("/api/pets/${millie.id}"))
                .andExpect(status().isNoContent)
    }

    @Test
    fun `delete pet not found`() {
        mockMvc.perform(delete("/api/pets/77777"))
                .andExpect(status().isNotFound)
    }

    private fun persistTestPet(name: String,
                               type: String,
                               birthDate: LocalDate = of(2020, FEBRUARY, 2)): Pet {
        val pet = Pet()
        pet.name = name
        pet.type = type
        pet.ownerId = testOwner.id
        pet.birthDate = birthDate

        return petRepository.saveAndFlush(pet)
    }

    private fun getPetAsJson(name: String,
                             type: String,
                             ownerId: Long,
                             birthDate: LocalDate): String {
        val pet = Pet()
        pet.name = name
        pet.type = type
        pet.ownerId = ownerId
        pet.birthDate = birthDate

        return objectMapper.writeValueAsString(pet)
    }

}