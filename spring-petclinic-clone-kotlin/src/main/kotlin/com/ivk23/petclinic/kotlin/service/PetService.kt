package com.ivk23.petclinic.kotlin.service

import com.ivk23.petclinic.kotlin.exception.ResourceNotFoundException
import com.ivk23.petclinic.kotlin.model.Pet
import com.ivk23.petclinic.kotlin.repository.PetRepository
import com.ivk23.petclinic.kotlin.repository.VisitRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PetService(private val petRepository: PetRepository,
                 private val visitRepository: VisitRepository) {

    companion object {
        val log: Logger = LoggerFactory
                .getLogger(PetService::class.java)
    }

    fun getPet(ownerId: Long, petId: Long): Pet {
        return petRepository
                .findByIdAndOwnerId(petId, ownerId)
                .orElseThrow {
                    ResourceNotFoundException("Pet (id=$petId) not found.")
                }
    }

    @Transactional
    fun deletePet(ownerId: Long, petId: Long) {
        petRepository
                .findByIdAndOwnerId(petId, ownerId)
                .ifPresentOrElse({
                    log.debug("Pet to be deleted: ${it.id}")
                    visitRepository.deleteInBatch(it.visits)
                    petRepository.delete(it)
                }, {
                    throw ResourceNotFoundException("Pet (id=$petId) not found.")
                })
    }
}