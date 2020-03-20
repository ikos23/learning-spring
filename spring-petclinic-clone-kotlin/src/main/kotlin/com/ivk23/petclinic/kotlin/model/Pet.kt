package com.ivk23.petclinic.kotlin.model

import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "pets")
class Pet : BaseEntity() {

    @NotNull
    @Size(min = 2, max = 15)
    @Column(name = "name")
    var name: String? = null

    @NotNull
    @Size(min = 2, max = 15)
    @Column(name = "type")
    var type: String? = null

    @NotNull
    @Column(name = "owner_id")
    var ownerId: Long? = null

    @NotNull
    @Column(name = "birth_date")
    var birthDate: LocalDate? = null

    @OneToMany
    @JoinColumn(name = "pet_id", referencedColumnName = "id")
    var visits: Set<Visit> = HashSet()

    override fun toString(): String {
        return "Pet(name=$name, type=$type, ownerId=$ownerId, birthDate=$birthDate, visits=$visits)"
    }

}