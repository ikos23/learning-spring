package com.ivk23.petclinic.kotlin.bootstrap

import com.ivk23.petclinic.kotlin.model.Owner
import com.ivk23.petclinic.kotlin.model.Pet
import com.ivk23.petclinic.kotlin.model.Vet
import com.ivk23.petclinic.kotlin.repository.OwnerRepository
import com.ivk23.petclinic.kotlin.repository.PetRepository
import com.ivk23.petclinic.kotlin.repository.VetRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.time.LocalDate

@Profile("dev")
@Component
class TestDataLoader(
        private val ownerRepo: OwnerRepository,
        private val vetRepo: VetRepository,
        private val petRepo: PetRepository
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        // persons
        ownerRepo.save(createTestOwner("Donald", "Duck", "Noname Str. 12", "SimCity", "123-4-555"))
        ownerRepo.save(createTestOwner("Chuck", "Norris", "West Side. 99", "SimCity", "555-666-777"))
        ownerRepo.save(createTestOwner("Pokemon", "Pikachu", "Planet Earth", "Planet Earth", "000-111-000"))
        ownerRepo.save(createTestOwner("Foo", "Bar", "Foo Str., 1", "SimCity", "123-000-555"))
        withPet(ownerRepo.save(createTestOwner("Tony", "Stark", "Ironman Ave.", "SimCity", "123-123-111")))

        vetRepo.save(createTestVet("Jack", "Sparrow"))
        vetRepo.save(createTestVet("Bart", "Simpson"))
        vetRepo.save(createTestVet("Donald", "Trump"))

        println("**** Loaded test data ***")
    }

    private fun createTestOwner(
            name: String,
            surname: String,
            address: String,
            city: String,
            telephone: String
    ): Owner {
        val o = Owner()

        o.firstName = name
        o.lastName = surname
        o.address = address
        o.city = city
        o.telephone = telephone

        return o
    }

    private fun withPet(owner: Owner): Owner {
        val testPet = Pet()
        testPet.name = "Mr Dog"
        testPet.birthDate = LocalDate.of(2020, 2, 5)
        testPet.ownerId = owner.id
        testPet.type = "Crocodile"
        petRepo.save(testPet)
        owner.pets = setOf(testPet).toMutableSet()
        return owner
    }

    private fun createTestVet(
            name: String,
            surname: String
    ): Vet {
        val vet = Vet()
        vet.firstName = name
        vet.lastName = surname

        return vet
    }
}