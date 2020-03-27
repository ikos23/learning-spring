package com.ivk23.petclinic.kotlin.service

import com.ivk23.petclinic.kotlin.exception.ResourceNotFoundException
import com.ivk23.petclinic.kotlin.model.Owner
import com.ivk23.petclinic.kotlin.model.Person
import com.ivk23.petclinic.kotlin.model.Pet
import com.ivk23.petclinic.kotlin.model.Vet
import com.ivk23.petclinic.kotlin.repository.OwnerRepository
import com.ivk23.petclinic.kotlin.repository.PetRepository
import com.ivk23.petclinic.kotlin.repository.VetRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Example
import org.springframework.data.domain.ExampleMatcher
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.function.Consumer

@Service
class PersonService(
        private val ownerRepo: OwnerRepository,
        private val vetRepo: VetRepository,
        private val petRepo: PetRepository
) {

    companion object {
        val log: Logger = LoggerFactory
                .getLogger(PersonService::class.java)
    }

    @Transactional
    fun create(owner: Owner): Owner {
        return ownerRepo.save(owner)
    }

    @Transactional
    fun addPet(ownerId: Long, pet: Pet) = run {
        ownerRepo.findById(ownerId)
                .ifPresentOrElse(
                        {
                            petRepo.save(pet)
                        },
                        {
                            throw ResourceNotFoundException("Owner (id=$ownerId) not found.")
                        }
                )
    }

    fun getAllPersonsCount(): Long = ownerRepo.count() + vetRepo.count()

    fun getAllOwners(): List<Owner> = ownerRepo.findAll(Sort.by(Sort.Order.asc("lastName")))

    fun getAllVets(): Set<Vet> = vetRepo.findAll().toSet()

    fun getVetById(id: Long): Vet = vetRepo
            .findById(id)
            .orElseThrow { ResourceNotFoundException("Vet (id=$id) not found.") }

    fun getOwnerById(id: Long): Owner = ownerRepo
            .findById(id)
            .orElseThrow { ResourceNotFoundException("Owner (id=$id) not found.") }

    fun getAllBy(firstName: String?, lastName: String?): Set<Any> {

        val matcher = ExampleMatcher.matching()
                .withMatcher("firstName") { matcher -> matcher.contains().ignoreCase() }
                .withMatcher("lastName") { matcher -> matcher.contains().ignoreCase() }
                .withIgnoreCase()
                .withIgnoreNullValues()

        fun <T : Person> exampleFor(obj: T, firstName: String?, lastName: String?): Person {
            obj.firstName = firstName
            obj.lastName = lastName
            return obj
        }

        @Suppress("UNCHECKED_CAST")
        val ownerExample = Example.of(exampleFor(Owner(), firstName, lastName), matcher) as Example<Owner>
        @Suppress("UNCHECKED_CAST") // lol
        val vetExample = Example.of(exampleFor(Vet(), firstName, lastName), matcher) as Example<Vet>

        val owners = ownerRepo.findAll(ownerExample)
        val vets = vetRepo.findAll(vetExample)

        val persons = HashSet<Any>()
        persons.addAll(owners.map {
            object {
                val type = "owner"
                val data = it
            }
        })

        persons.addAll(vets.map {
            object {
                val type = "vet"
                val data = it
            }
        })

        return persons
    }

    @Transactional
    fun update(owner: Owner) {
        ownerRepo.findById(owner.id!!).ifPresentOrElse(
                // just to demo I do not remove Redundant SAM-constructors
                Consumer { ownerRepo.save(owner) },
                Runnable { throw ResourceNotFoundException("Owner (id=${owner.id}) not found.") }
        )
    }

    @Transactional
    fun delete(ownerId: Long) {
        ownerRepo.findById(ownerId)
                .ifPresentOrElse(
                        { ownerRepo.delete(it) },
                        { throw ResourceNotFoundException("Owner (id=$ownerId) not found.") }
                )
    }

    /**
     * Update only certain fields of owner.
     */
    @Transactional
    fun patch(ownerId: Long, owner: Owner): Owner {
        val ownerFromDB = ownerRepo
                .findById(ownerId)
                .orElseThrow { ResourceNotFoundException("Owner (id=$ownerId) not found.") }

        if (owner.firstName != null) ownerFromDB.firstName = owner.firstName
        if (owner.lastName != null) ownerFromDB.lastName = owner.lastName
        if (owner.city != null) ownerFromDB.city = owner.city
        if (owner.address != null) ownerFromDB.address = owner.address
        if (owner.telephone != null) ownerFromDB.telephone = owner.telephone

        return ownerFromDB
    }
}