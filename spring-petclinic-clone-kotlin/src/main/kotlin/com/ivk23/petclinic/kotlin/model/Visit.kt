package com.ivk23.petclinic.kotlin.model

import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "visits")
class Visit : BaseEntity() {

    @NotNull
    @Column(name = "visit_date")
    var visitDate: LocalDate? = null

    @Size(min = 2, max = 100)
    @Column(name = "description", length = 100)
    var description: String? = null

    @NotNull
    @Column(name = "pet_id")
    var petId: Long? = null

}