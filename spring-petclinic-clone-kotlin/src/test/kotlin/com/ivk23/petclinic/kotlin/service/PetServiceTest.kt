package com.ivk23.petclinic.kotlin.service

import com.ivk23.petclinic.kotlin.exception.ResourceNotFoundException
import com.ivk23.petclinic.kotlin.model.Owner
import com.ivk23.petclinic.kotlin.model.Pet
import com.ivk23.petclinic.kotlin.model.Visit
import com.ivk23.petclinic.kotlin.repository.OwnerRepository
import com.ivk23.petclinic.kotlin.repository.PetRepository
import com.ivk23.petclinic.kotlin.repository.VisitRepository
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate
import kotlin.test.assertEquals

// Annotation for a JPA test that focuses only on JPA components.
// Using this annotation will disable full auto-configuration
// and instead apply only configuration relevant to JPA tests.
@DataJpaTest
@ActiveProfiles("default")
class PetServiceTest {

    companion object {

        lateinit var classUnderTest: PetService

        // we need to know DB ids for our tests, so let's keep all
        // data created for tests here
        var testPet = Pet()
        var testOwner = Owner()

        @BeforeAll
        @JvmStatic
        internal fun beforeAll(@Autowired ownerRepository: OwnerRepository,
                               @Autowired petRepository: PetRepository,
                               @Autowired visitRepository: VisitRepository) {

            classUnderTest = PetService(petRepository, visitRepository)
            createTestData(ownerRepository, petRepository, visitRepository)
            println("****** PetServiceTest.beforeAll called ... ******")
        }

        private fun createTestData(ownerRepository: OwnerRepository,
                                   petRepository: PetRepository,
                                   visitRepository: VisitRepository) {
            val o1 = Owner()
            o1.firstName = "Foo"
            o1.lastName = "Bar"
            o1.telephone = "123456"
            testOwner = ownerRepository.save(o1)

            val p1 = Pet()
            p1.ownerId = testOwner.id
            p1.name = "Lola"
            p1.type = "Dog"
            p1.birthDate = LocalDate.of(2019, 6, 7)
            testPet = petRepository.save(p1)

            val v1 = Visit()
            v1.description = "Visit 1"
            v1.petId = testPet.id
            v1.visitDate = LocalDate.of(2020, 5, 6)
            visitRepository.save(v1)
        }

        @AfterAll
        @JvmStatic
        internal fun afterAll(@Autowired ownerRepository: OwnerRepository,
                              @Autowired petRepository: PetRepository,
                              @Autowired visitRepository: VisitRepository) {
            visitRepository.deleteAll()
            petRepository.deleteAll()
            ownerRepository.deleteAll()
            println("****** PetServiceTest.afterAll called ... ******")
        }
    }


    @Test
    fun getPet() {
        val pet = classUnderTest.getPet(testOwner.id!!, testPet.id!!)
        assertEquals("Lola", pet.name)
        assertEquals("Dog", pet.type)
        assertEquals(testOwner.id, pet.ownerId)
    }

    @Test
    fun getPetNotFound() {
        val exception = assertThrows<ResourceNotFoundException> {
            classUnderTest.getPet(testOwner.id!!, 123)
        }
        assertEquals("Pet (id=123) not found.", exception.message)
    }

    @Test
    fun deletePet() {
        classUnderTest.deletePet(testOwner.id!!, testPet.id!!)

        assertThrows<ResourceNotFoundException> {
            classUnderTest.getPet(testOwner.id!!, testPet.id!!)
        }
    }
}