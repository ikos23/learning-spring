package com.ivk23.petclinic.kotlin.model

import javax.persistence.*

@Entity
@Table(name = "vets")
class Vet : Person() {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "vet_specialities",
        joinColumns = [JoinColumn(name = "vet_id")],
        inverseJoinColumns = [JoinColumn(name = "speciality_id")]
    )
    var specialities: Set<Speciality> = HashSet()

}