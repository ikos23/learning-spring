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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Visit) return false
        if (!super.equals(other)) return false

        if (visitDate != other.visitDate) return false
        if (description != other.description) return false
        if (petId != other.petId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + (visitDate?.hashCode() ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (petId?.hashCode() ?: 0)
        return result
    }


}