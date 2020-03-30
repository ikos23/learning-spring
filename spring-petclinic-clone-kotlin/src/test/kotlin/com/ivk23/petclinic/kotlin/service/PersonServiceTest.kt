package com.ivk23.petclinic.kotlin.service

import com.ivk23.petclinic.kotlin.SpringBootTestBase
import com.ivk23.petclinic.kotlin.exception.ResourceNotFoundException
import com.ivk23.petclinic.kotlin.model.Owner
import com.ivk23.petclinic.kotlin.model.Pet
import com.ivk23.petclinic.kotlin.model.Vet
import com.ivk23.petclinic.kotlin.repository.VetRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDate
import java.time.Month.JANUARY
import java.time.Month.JUNE
import kotlin.test.*


class PersonServiceTest : SpringBootTestBase() {

    @Autowired
    private lateinit var vetRepo: VetRepository

    private lateinit var classUnderTest: PersonService

    @BeforeEach
    fun setUp() {
        classUnderTest = PersonService(ownerRepository, vetRepo, petRepository)
    }

    @AfterEach
    fun tearDown() {
        petRepository.deleteAll()
        ownerRepository.deleteAll()
        vetRepo.deleteAll()
    }

    @Test
    fun create() {
        // given
        val owner = createTestOwner("create")

        // when
        val created = classUnderTest.create(owner)
        println("new test owner created: $created")

        // then
        assertNotNull(created.id)

        val byId = ownerRepository.findById(created.id!!)
        assertTrue { byId.isPresent }
        assertNotSame(created, byId.get())
        assertEquals(byId.get().firstName, created.firstName)
        assertEquals(byId.get().lastName, created.lastName)
        assertEquals(byId.get().address, created.address)
        assertEquals(byId.get().city, created.city)
        assertEquals(byId.get().telephone, created.telephone)

    }

    @Test
    fun addPet() {
        // given
        var owner = createTestOwner("addPet")
        owner = ownerRepository.saveAndFlush(owner)

        val pet = createTestPet(test = "addPet", name = "Bob", owner = owner)
        petRepository.saveAndFlush(pet)
        assertEquals(1, petRepository.findAllByOwnerId(owner.id!!).size)

        val newPet = createTestPet(
                test = "addPet",
                name = "Yoyo",
                owner = owner,
                birthDate = LocalDate.of(2018, JUNE, 22))
        println("pet to be created: $newPet")

        // when
        classUnderTest.addPet(owner.id!!, newPet)

        // then
        val pets = petRepository.findAllByOwnerId(owner.id!!)
        assertEquals(2, pets.size)

        pets
                .filter {
                    it.name.equals("Yoyo")
                }
                .forEach {
                    assertEquals(it.birthDate, LocalDate.of(2018, JUNE, 22))
                }
    }

    @Test
    fun `addPet owner does not exist case`() {
        val exc = assertThrows<ResourceNotFoundException> { classUnderTest.addPet(123, Pet()) }
        assertEquals("Owner (id=123) not found.", exc.message)
    }

    @Test
    fun `getAllPersonsCount when nobody has been created`() {
        assertEquals(0, classUnderTest.getAllPersonsCount())
    }

    @Test
    fun getAllPersonsCount() {

        val owner1 = createTestOwner("getAllPersonsCount")
        val owner2 = createTestOwner("getAllPersonsCount")
        val vet1 = createTestVet("getAllPersonsCount")

        ownerRepository.saveAndFlush(owner1)
        ownerRepository.saveAndFlush(owner2)
        vetRepo.saveAndFlush(vet1)

        assertEquals(3, classUnderTest.getAllPersonsCount())
    }

    @Test
    fun getAllOwners() {
        // given
        val owner1 = createTestOwner("getAllOwners")
        val owner2 = createTestOwner("getAllOwners")
        ownerRepository.saveAndFlush(owner1)
        ownerRepository.saveAndFlush(owner2)

        // when
        val allOwners = classUnderTest.getAllOwners()

        // then
        assertEquals(2, allOwners.size)
        allOwners.forEach {
            assertEquals("getAllOwners_FirstName", it.firstName)
            assertEquals("getAllOwners_LastName", it.lastName)
        }
    }

    @Test
    fun getAllVets() {
        // given
        val vet1 = createTestVet("getAllVets")
        vetRepo.saveAndFlush(vet1)

        // when
        val allVets = classUnderTest.getAllVets()

        // then
        assertEquals(1, allVets.size)
    }

    @Test
    fun getVetById() {
        // given
        val vet1 = createTestVet("getVetById")
        val created = vetRepo.saveAndFlush(vet1)

        // when
        val vet = classUnderTest.getVetById(created.id!!)

        // then
        assertEquals("getVetById_VetFirstName", vet.firstName)
        assertEquals("getVetById_VetLastName", vet.lastName)
    }

    @Test
    fun `getVetById negative case no vet found`() {
        assertThrows<ResourceNotFoundException> { classUnderTest.getVetById(777) }
                .also {
                    assertEquals("Vet (id=777) not found.", it.message)
                }
    }

    @Test
    fun getOwnerById() {
        // given
        val owner1 = createTestOwner("getOwnerById")
        val created = ownerRepository.saveAndFlush(owner1)

        // when
        val ownerById = classUnderTest.getOwnerById(created.id!!)

        // then
        assertNotSame(owner1, ownerById)
        assertNotSame(created, ownerById)
        assertEquals(created.id, ownerById.id)
        assertEquals("getOwnerById_FirstName", ownerById.firstName)
        assertEquals("getOwnerById_LastName", ownerById.lastName)
        assertEquals("getOwnerById_Address", ownerById.address)
        assertEquals("getOwnerById_City", ownerById.city)
        assertEquals("getOwnerById_123456789", ownerById.telephone)
    }

    @Test
    fun `getOwnerById negative case no owner found`() {
        assertThrows<ResourceNotFoundException> { classUnderTest.getOwnerById(777) }
                .also {
                    assertEquals("Owner (id=777) not found.", it.message)
                }
    }

    @Test
    fun getAllBy() {
        // given
        val owner1 = createTestOwner("getAllBy")
        val owner2 = createTestOwner("getAllBy", "foo", "bar")
        ownerRepository.saveAndFlush(owner1)
        ownerRepository.saveAndFlush(owner2)

        val vet = createTestVet("getAllBy")
        vetRepo.saveAndFlush(vet)

        // when
        val allBy = classUnderTest.getAllBy("FirstName", null)

        assertEquals(2, allBy.size)
    }

    @Test
    fun update() {
        // given
        val owner1 = createTestOwner("update")
        val created = ownerRepository.saveAndFlush(owner1)
        created.firstName = "UPDATED"
        created.lastName = "UPDATED_LAST"

        // when
        classUnderTest.update(created)

        // then
        val byId = ownerRepository.findById(created.id!!)
        assertEquals("UPDATED", byId.get().firstName)
        assertEquals("UPDATED_LAST", byId.get().lastName)
    }

    @Test
    fun delete() {
        // given
        val owner1 = createTestOwner("delete")
        val created = ownerRepository.saveAndFlush(owner1)

        assertTrue { ownerRepository.findById(created.id!!).isPresent }

        // when
        classUnderTest.delete(created.id!!)

        // then
        assertFalse { ownerRepository.findById(created.id!!).isPresent }
    }

    @Test
    fun patch() {
        // given
        val owner1 = createTestOwner("patch")
        val created = ownerRepository.saveAndFlush(owner1)

        val newOwner = Owner()
        newOwner.firstName = "New First Name"
        newOwner.lastName = "New Last Name"
        newOwner.address = "New Address"
        newOwner.city = "New City"
        newOwner.telephone = "000 111 222"

        val patched = classUnderTest.patch(created.id!!, newOwner)

        assertNotSame(newOwner, patched)
        assertEquals("New First Name", patched.firstName)
        assertEquals("New Last Name", patched.lastName)
        assertEquals("New Address", patched.address)
        assertEquals("New City", patched.city)
        assertEquals("000 111 222", patched.telephone)

    }

    private fun createTestOwner(
            test: String,
            firstName: String = "${test}_FirstName",
            lastName: String = "${test}_LastName"
    ): Owner {
        val owner = Owner()
        owner.firstName = firstName
        owner.lastName = lastName
        owner.address = "${test}_Address"
        owner.city = "${test}_City"
        owner.telephone = "${test}_123456789"
        return owner
    }

    private fun createTestPet(
            test: String,
            name: String = "${test}_Donald",
            owner: Owner? = null,
            birthDate: LocalDate = LocalDate.of(2020, JANUARY, 1)
    ): Pet {
        val pet = Pet()
        pet.ownerId = owner?.id
        pet.name = name
        pet.birthDate = birthDate
        pet.type = "Dog"
        return pet
    }

    private fun createTestVet(test: String): Vet {
        val vet = Vet()
        vet.firstName = "${test}_VetFirstName"
        vet.lastName = "${test}_VetLastName"
        return vet
    }
}