package com.ivk23.petclinic.kotlin.service

import com.ivk23.petclinic.kotlin.model.Visit
import com.ivk23.petclinic.kotlin.repository.VisitRepository
import org.springframework.data.domain.Sort.Order.desc
import org.springframework.data.domain.Sort.by
import org.springframework.stereotype.Service

@Service
class VisitService(private val visitRepository: VisitRepository) {

    fun create(visit: Visit) = this.visitRepository.save(visit)

    fun getAll() = this.visitRepository.findAll(by(desc("visitDate")))

}