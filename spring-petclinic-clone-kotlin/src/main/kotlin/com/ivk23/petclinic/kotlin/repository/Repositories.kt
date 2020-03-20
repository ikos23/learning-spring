package com.ivk23.petclinic.kotlin.repository

import com.ivk23.petclinic.kotlin.model.Owner
import com.ivk23.petclinic.kotlin.model.Pet
import com.ivk23.petclinic.kotlin.model.Vet
import com.ivk23.petclinic.kotlin.model.Visit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.query.QueryByExampleExecutor
import java.util.*

@NoRepositoryBean
interface PersonRepository<T> : JpaRepository<T, Long>, QueryByExampleExecutor<T>

interface OwnerRepository : PersonRepository<Owner>

interface VetRepository : PersonRepository<Vet>

interface PetRepository : JpaRepository<Pet, Long> {

    fun findAllByOwnerId(id: Long): List<Pet>

    fun findByIdAndOwnerId(id: Long, ownerId: Long): Optional<Pet>

}

interface VisitRepository : JpaRepository<Visit, Long>