package com.ivk23.petclinic.kotlin.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "specialities")
class Speciality : BaseEntity() {

    @Column(name = "description")
    var description: String? = null

    @ManyToMany(mappedBy = "specialities")
    var vets: Set<Vet> = HashSet()

}