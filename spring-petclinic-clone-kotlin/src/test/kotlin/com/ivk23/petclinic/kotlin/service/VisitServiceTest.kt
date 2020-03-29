package com.ivk23.petclinic.kotlin.service

import com.ivk23.petclinic.kotlin.model.Owner
import com.ivk23.petclinic.kotlin.model.Pet
import com.ivk23.petclinic.kotlin.model.Visit
import com.ivk23.petclinic.kotlin.repository.OwnerRepository
import com.ivk23.petclinic.kotlin.repository.PetRepository
import com.ivk23.petclinic.kotlin.repository.VisitRepository
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.support.TransactionTemplate
import java.time.LocalDate

@DataJpaTest
@ActiveProfiles("default")
class VisitServiceTest {

    companion object {

        lateinit var classUnderTest: VisitService

        // we need to know DB ids for our tests, so let's keep all
        // data created for tests here
        var testPet = Pet()
        var testOwner = Owner()

        @BeforeAll
        @JvmStatic
        internal fun beforeAll(@Autowired ownerRepository: OwnerRepository,
                               @Autowired petRepository: PetRepository,
                               @Autowired visitRepository: VisitRepository) {

            classUnderTest = VisitService(visitRepository)
            createTestData(ownerRepository, petRepository, visitRepository)
            println("****** VisitServiceTest.beforeAll called ... ******")
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
            println("****** VisitServiceTest.afterAll called ... ******")
        }
    }

    @Autowired
    private lateinit var txTemplate: TransactionTemplate


    @Order(1)
    @Test
    fun create() {
        val vis = Visit()
        vis.description = "Visit 2"
        vis.petId = testPet.id
        vis.visitDate = LocalDate.of(2021, 2, 16)

        txTemplate.executeWithoutResult {
            val created = classUnderTest.create(vis)
            assertNotNull(created.id)
        }

    }

    @Order(2)
    @Test
    fun getAll() {
        assertEquals(1, classUnderTest.getAll().size)
    }
}